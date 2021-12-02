package dev.klevente.coupunch.couponmanager.checkout

import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutEvent
import dev.klevente.coupunch.couponmanager.checkout.dto.PurchasedProductEvent
import dev.klevente.coupunch.couponmanager.checkout.dto.RedeemedRewardEvent
import dev.klevente.coupunch.couponmanager.coupon.CouponService
import dev.klevente.coupunch.couponmanager.coupon.calculateDiscountedPrice
import dev.klevente.coupunch.couponmanager.product.ProductService
import dev.klevente.coupunch.library.util.mapToArray
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CheckoutEventPublisher(
    private val amqpTemplate: AmqpTemplate,
    @Value("\${amqp.exchange.analytics}") private val analyticsTopicExchangeName: String,
    private val productService: ProductService,
    private val couponService: CouponService
) {
    fun checkout(
        userId: Long,
        productIds: List<ProductIdWithAmount>,
        rewardIds: List<RewardIdWithAmountAndCouponData>
    ) {
        val event = buildEvent(userId, productIds, rewardIds)
        amqpTemplate.convertAndSend(analyticsTopicExchangeName, "analytics.checkout", event)
    }

    private fun buildEvent(
        userId: Long,
        productIds: List<ProductIdWithAmount>,
        rewardIds: List<RewardIdWithAmountAndCouponData>
    ): CheckoutEvent {
        val purchasedProductEvents = productIds.mapToArray {
            val product = productService.getProduct(it.id)
            PurchasedProductEvent(
                name = product.name,
                amount = it.amount,
                price = product.price.toDouble()
            )
        }

        val redeemedRewardEvents = rewardIds.mapToArray {
            val coupon = couponService.getCoupon(it.couponId)
            val rewardLevelRedeemed = coupon.rewards[it.redeemLevel]
            val product = productService.getProduct(it.id)
            RedeemedRewardEvent(
                name = product.name,
                amount = it.amount,
                price = product.calculateDiscountedPrice(
                    discountType = rewardLevelRedeemed.discountType,
                    discount = rewardLevelRedeemed.discount
                ).toDouble(),
                couponName = coupon.name,
                redeemLevel = it.redeemLevel
            )
        }

        return CheckoutEvent(
            timestamp = LocalDateTime.now(),
            userId = userId,
            products = purchasedProductEvents,
            rewards = redeemedRewardEvents
        )
    }
}

class ProductIdWithAmount(
    val id: Long,
    val amount: Int
)

class RewardIdWithAmountAndCouponData(
    val id: Long,
    val amount: Int,
    val couponId: Long,
    val redeemLevel: Int
)

