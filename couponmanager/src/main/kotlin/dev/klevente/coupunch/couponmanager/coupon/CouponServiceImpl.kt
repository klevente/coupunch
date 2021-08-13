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
) : CouponService {

    override fun getCoupon(id: Long) =
        couponRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(Coupon::class, id)

    @Transactional
    override fun addCoupon(request: CouponCreateRequest): Coupon {

        val coupon = couponRepository.save(
            request.toCouponWithoutRewards()
        )
        val rewards = rewardRepository.saveAll(
            request.rewards.map { it.toReward(owner = coupon) }
        )
        coupon.rewards = rewards

        return coupon
    }

    @Transactional
    override fun updateCoupon(id: Long, request: CouponUpdateRequest) {
        val coupon = getCoupon(id)

        coupon.apply {
            name = request.name
            type = request.type.toCouponType()
            eligibleProducts = convertToEligibleProducts(request.eligibleItems.products)
            eligibleProductGroups = convertToEligibleProductGroups(request.eligibleItems.productGroups)

        }

        val oldRewards = coupon.rewards
        val newRewards = rewardRepository.saveAll(
            request.rewards.map { it.toReward(owner = coupon) }
        )
        coupon.rewards = newRewards

        rewardRepository.deleteAll(oldRewards)
    }

    @Transactional
    override fun deleteCoupon(id: Long) {
        val coupon = getCoupon(id)
        couponRepository.delete(coupon)
    }

    override fun getCouponResponse(id: Long) = getCoupon(id).toResponse()

    override fun getCouponsResponse() = couponRepository.findAll().toResponse()

    override fun removeDeletedProductFromCoupons(product: Product) {
        // get coupons which have this product as eligible TODO
        val couponsWithEligibleProduct = emptyList<Coupon>()
        val couponsToDeleteAsNoEligibles = couponsWithEligibleProduct.fold(
            mutableListOf<Coupon>()
        ) { coupons, coupon ->
            // check if they have other eligibles
            if (coupon.hasMoreEligibleProductsOrAnyProductGroups()) {
                // if they do, remove the eligible product entry
                coupon.eligibleProducts.remove(product)
            } else {
                // if they dont, remove the coupon
                coupons.add(coupon)
            }
            coupons
        }

        couponRepository.deleteAll(couponsToDeleteAsNoEligibles)

        // get coupons which have this product as a reward
        val couponsWithRewardProduct = emptyList<Coupon>()
        val couponsToDeleteAsNoRewards = couponsWithRewardProduct.fold(mutableListOf<Coupon>()) { coupons, coupon ->
            val rewardsToDelete = coupon.rewards.fold(
                mutableListOf<Reward>()
            ) { rewards, reward ->
                // check if they have other reward entries
                if (reward.hasMoreRewardProductsOrAnyProductGroups()) {
                    // if they do, remove the reward product entry
                    reward.products.remove(product)
                } else {
                    // if they dont, remove the reward
                    rewards.add(reward)
                }
                rewards
            }

            rewardsToDelete.forEach { coupon.rewards.remove(it) }

            // if all rewards have been removed for a coupon, delete it as well
            if (coupon.rewards.isEmpty()) {
                coupons.add(coupon)
            }

            coupons
        }

        couponRepository.deleteAll(couponsToDeleteAsNoRewards)
    }

    override fun removeDeletedProductGroupFromCoupons(productGroup: ProductGroup) {

        // get coupons which have this product group as eligible TODO
        val couponsWithEligibleProductGroup = emptyList<Coupon>()
        val couponsToDeleteAsNoEligibles = couponsWithEligibleProductGroup.fold(
            mutableListOf<Coupon>()
        ) { coupons, coupon ->
            // check if they have other eligibles
            if (coupon.hasMoreEligibleProductGroupsOrAnyProducts()) {
                // if they do, remove the eligible product group entry
                coupon.eligibleProductGroups.remove(productGroup)
            } else {
                // if they dont, remove the coupon
                coupons.add(coupon)
            }
            coupons
        }

        couponRepository.deleteAll(couponsToDeleteAsNoEligibles)

        // get coupons which have this product group as a reward
        val couponsWithRewardProductGroup = emptyList<Coupon>()
        val couponsToDeleteAsNoRewards = couponsWithRewardProductGroup.fold(mutableListOf<Coupon>()) { coupons, coupon ->
            val rewardsToDelete = coupon.rewards.fold(
                mutableListOf<Reward>()
            ) { rewards, reward ->
                // check if they have other reward entries
                if (reward.hasMoreRewardProductGroupsOrAnyProducts()) {
                    // if they do, remove the reward product group entry
                    reward.productGroups.remove(productGroup)
                } else {
                    // if they dont, remove the reward
                    rewards.add(reward)
                }
                rewards
            }

            rewardsToDelete.forEach { coupon.rewards.remove(it) }

            // if all rewards have been removed for a coupon, delete it as well
            if (coupon.rewards.isEmpty()) {
                coupons.add(coupon)
            }

            coupons
        }

        couponRepository.deleteAll(couponsToDeleteAsNoRewards)
    }

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

    private fun convertToEligibleProducts(
        eligibleProducts: Array<EligibleProductRequest>
    ): EligibleProducts {
        val ids = eligibleProducts.map { it.id }
        val products = getProductsByIds(ids)

        return products.mapIndexed { index, product ->
            product to eligibleProducts[index].points
        }.toMutableMap()
    }

    private fun convertToEligibleProductGroups(
        eligibleProductGroups: Array<EligibleProductGroupRequest>
    ): EligibleProductGroups {
        val ids = eligibleProductGroups.map { it.id }
        val productGroups = getProductGroupsByIds(ids)

        return productGroups.mapIndexed { index, productGroup ->
            productGroup to eligibleProductGroups[index].points
        }.toMutableMap()
    }

    private fun convertToProductRewards(
        productRewards: Array<ProductRewardRequest>
    ): ProductRewards {
        val ids = productRewards.map { it.id }
        val products = getProductsByIds(ids)
        return products.mapIndexed { index, product ->
            product to productRewards[index].amount
        }.toMutableMap()
    }

    private fun convertToProductGroupRewards(
        productGroupRewards: Array<ProductGroupRewardRequest>
    ): ProductGroupRewards {
        val ids = productGroupRewards.map { it.id }
        val productGroups = getProductGroupsByIds(ids)
        return productGroups.mapIndexed { index, productGroup ->
            productGroup to productGroupRewards[index].amount
        }.toMutableMap()
    }

    private fun CouponCreateRequest.toCouponWithoutRewards() = Coupon(
        name = name,
        type = type.toCouponType(),
        eligibleProducts = convertToEligibleProducts(eligibleItems.products),
        eligibleProductGroups = convertToEligibleProductGroups(eligibleItems.productGroups)
    )

    private fun RewardRequest.toReward(owner: Coupon) = Reward(
        threshold = threshold,
        discountType = discountType.toDiscountType(),
        discount = discount,
        products = convertToProductRewards(products),
        productGroups = convertToProductGroupRewards(productGroups),
        coupon = owner
    )
}