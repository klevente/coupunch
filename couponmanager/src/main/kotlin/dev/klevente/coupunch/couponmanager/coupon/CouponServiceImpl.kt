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
            Coupon(
                name = request.name,
                type = request.type.toCouponType(),
                eligibleProducts = convertToEligibleProducts(request.eligibleItems.products),
                eligibleProductGroups = convertToEligibleProductGroups(request.eligibleItems.productGroups)
            )
        )

        val rewards = rewardRepository.saveAll(
            request.rewards.map {
                Reward(
                    threshold = it.threshold,
                    discountType = it.discountType.toDiscountType(),
                    discount = it.discount,
                    products = convertToProductRewards(it.products),
                    productGroups = convertToProductGroupRewards(it.productGroups),
                    coupon = coupon
                )
            }
        )

        coupon.rewards = rewards

        return coupon
    }

    @Transactional
    override fun updateCoupon(id: Long, request: CouponUpdateRequest) {
        val coupon = getCoupon(id)

        coupon.apply {

        }
    }

    @Transactional
    override fun deleteCoupon(id: Long) {
        val coupon = getCoupon(id)
        couponRepository.delete(coupon)
    }

    override fun getCouponResponse(id: Long) = getCoupon(id).toResponse()

    override fun getCouponsResponse() = couponRepository.findAll().toResponse()

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
}