package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.coupon.dto.*
import dev.klevente.coupunch.couponmanager.product.*
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.util.toMutableMap
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponServiceImpl(
    private val log: Logger,
    private val couponRepository: CouponRepository,
    private val rewardRepository: RewardRepository,
    private val productRepository: ProductRepository,
    private val productGroupRepository: ProductGroupRepository
) : CouponActions, CouponService {

    override fun getCoupon(id: Long) =
        couponRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(Coupon::class, id)

    @Transactional
    override fun addCoupon(request: CouponCreateRequest): CouponResponse {

        val coupon = couponRepository.save(
            request.toCouponWithoutRewards(
                getProducts = this::getProductsByIds,
                getProductGroups = this::getProductGroupsByIds
            )
        )
        val rewards = rewardRepository.saveAll(
            request.rewards.map {
                it.toReward(
                    owner = coupon,
                    getProducts = this::getProductsByIds,
                    getProductGroups = this::getProductGroupsByIds
                )
            }
        )
        coupon.rewards = rewards

        return coupon.toResponse()
    }

    @Transactional
    override fun updateCoupon(id: Long, request: CouponUpdateRequest): CouponResponse {
        val coupon = getCoupon(id)

        coupon.apply {
            name = request.name
            type = request.type.toCouponType()
            eligibleProducts = request.eligibleItems.products.toDomain(
                getProducts = this@CouponServiceImpl::getProductsByIds
            )
            eligibleProductGroups = request.eligibleItems.productGroups.toDomain(
                getProductGroups = this@CouponServiceImpl::getProductGroupsByIds
            )

        }

        val oldRewards = coupon.rewards
        val newRewards = rewardRepository.saveAll(
            request.rewards.map {
                it.toReward(
                    owner = coupon,
                    getProducts = this::getProductsByIds,
                    getProductGroups = this::getProductGroupsByIds
                )
            }
        )
        coupon.rewards = newRewards

        rewardRepository.deleteAll(oldRewards)

        return coupon.toResponse()
    }

    @Transactional
    override fun deleteCoupon(id: Long): CouponResponse {
        val coupon = getCoupon(id)
        val ret = coupon.toResponse()

        couponRepository.delete(coupon)

        return ret
    }

    override fun getCouponResponse(id: Long) = getCoupon(id).toResponse()

    override fun getCouponsResponse() = couponRepository.findAll().toResponse()

    override fun removeDeletedProductFromCoupons(product: Product) {
        // get coupons which have this product as eligible
        val couponsWithEligibleProduct = couponRepository.findCouponsWithEligibleProduct(product)
        val couponsToDeleteAsNoEligibles = couponsWithEligibleProduct.fold(
            mutableListOf<Coupon>()
        ) { coupons, coupon ->
            // check if they have other eligibles
            if (coupon.hasMoreEligibleProductsOrAnyProductGroups()) {
                // if they do, remove the eligible product entry
                coupon.eligibleProducts.remove(product)
            } else {
                // if they don't, remove the coupon
                coupons.add(coupon)
            }
            coupons
        }

        couponRepository.deleteAll(couponsToDeleteAsNoEligibles)

        // get coupons which have this product as a reward
        val couponsWithRewardProduct = couponRepository.findCouponsWithRewardProduct(product)
        val couponsToDeleteAsNoRewards = couponsWithRewardProduct.fold(
            mutableListOf<Coupon>()
        ) { coupons, coupon ->
            val rewardsToDelete = coupon.rewards.fold(
                mutableListOf<Reward>()
            ) { rewards, reward ->
                // check if they have other reward entries
                if (reward.hasMoreRewardProductsOrAnyProductGroups()) {
                    // if they do, remove the reward product entry
                    reward.products.remove(product)
                } else {
                    // if they don't, remove the reward
                    rewards.add(reward)
                }
                rewards
            }

            coupon.rewards.removeAll(rewardsToDelete)

            // if all rewards have been removed for a coupon, delete it as well
            if (coupon.rewards.isEmpty()) {
                coupons.add(coupon)
            }

            coupons
        }

        couponRepository.deleteAll(couponsToDeleteAsNoRewards)
    }

    override fun removeDeletedProductGroupFromCoupons(productGroup: ProductGroup) {
        // get coupons which have this product group as eligible
        val couponsWithEligibleProductGroup = couponRepository.findCouponsWithEligibleProductGroup(productGroup)
        val couponsToDeleteAsNoEligibles = couponsWithEligibleProductGroup.fold(
            mutableListOf<Coupon>()
        ) { coupons, coupon ->
            // check if they have other eligibles
            if (coupon.hasMoreEligibleProductGroupsOrAnyProducts()) {
                // if they do, remove the eligible product group entry
                coupon.eligibleProductGroups.remove(productGroup)
            } else {
                // if they don't, remove the coupon
                coupons.add(coupon)
            }
            coupons
        }

        couponRepository.deleteAll(couponsToDeleteAsNoEligibles)

        // get coupons which have this product group as a reward
        val couponsWithRewardProductGroup = couponRepository.findCouponsWithRewardProductGroup(productGroup)
        val couponsToDeleteAsNoRewards =
            couponsWithRewardProductGroup.fold(
                mutableListOf<Coupon>()
            ) { coupons, coupon ->
                val rewardsToDelete = coupon.rewards.fold(
                    mutableListOf<Reward>()
                ) { rewards, reward ->
                    // check if they have other reward entries
                    if (reward.hasMoreRewardProductGroupsOrAnyProducts()) {
                        // if they do, remove the reward product group entry
                        reward.productGroups.remove(productGroup)
                    } else {
                        // if they don't, remove the reward
                        rewards.add(reward)
                    }
                    rewards
                }

                coupon.rewards.removeAll(rewardsToDelete)

                // if all rewards have been removed for a coupon, delete it as well
                if (coupon.rewards.isEmpty()) {
                    coupons.add(coupon)
                }

                coupons
            }

        couponRepository.deleteAll(couponsToDeleteAsNoRewards)
    }

    override fun getRelevantCouponsFor(product: Product) =
        couponRepository.findCouponsWithEligibleProduct(product) +
                couponRepository.findCouponsWithEligibleProductInEligibleProductGroups(product)

    private fun getProductsByIds(ids: Collection<Long>): List<Product> {
        val products = productRepository.findAllById(ids)
        if (products.size != ids.size) {
            throw IllegalArgumentException("Some IDs are not valid products!")
        }
        return products
    }

    private fun getProductGroupsByIds(ids: Collection<Long>): List<ProductGroup> {
        val productGroups = productGroupRepository.findAllById(ids)
        if (productGroups.size != ids.size) {
            throw IllegalArgumentException("Some IDs are not valid product groups!")
        }
        return productGroups
    }
}