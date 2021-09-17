package dev.klevente.coupunch.couponmanager.checkout

import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutRequest
import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutResponse
import dev.klevente.coupunch.couponmanager.checkout.dto.PurchasedProduct
import dev.klevente.coupunch.couponmanager.checkout.dto.RedeemedCoupon
import dev.klevente.coupunch.couponmanager.coupon.*
import dev.klevente.coupunch.couponmanager.customer.CustomerCoupons
import dev.klevente.coupunch.couponmanager.customer.CustomerService
import dev.klevente.coupunch.couponmanager.product.ProductService
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CheckoutServiceImpl(
    private val log: Logger,
    private val customerService: CustomerService,
    private val productService: ProductService,
    private val couponService: CouponService,
    private val checkoutEventPublisher: CheckoutEventPublisher
) : CheckoutActions {
    @Transactional
    override fun checkout(username: String, request: CheckoutRequest): CheckoutResponse {
        val customer = customerService.getCustomerByUsername(username)

        // gather coupons that need to be redeemed
        val couponEntriesToUpdate = filterRequiredCoupons(
            customerCoupons = customer.coupons,
            requestCoupons = request.coupons
        )

        val couponsRedeemedAtLevel = couponEntriesToUpdate.map { it.getRedeemLevel() }

        // process coupons by subtracting the required amount of progress
        processRedeemedCoupons(
            customerCoupons = customer.coupons,
            redeemedCoupons = couponEntriesToUpdate
        )

        // process products by adding the required progress to each coupon they affect
        processPurchasedProducts(
            products = request.products,
            customerCoupons = customer.coupons
        )


        checkoutEventPublisher.checkout(
            userId = customer.id,
            productIds = request.products.map {
                ProductIdWithAmount(it.id, it.amount)
            },
            rewardIds = request.coupons.mapIndexed { index, it ->
                RewardIdWithAmountAndCouponData(
                    id = it.productId,
                    amount = it.amount,
                    couponId = it.id,
                    redeemLevel = couponsRedeemedAtLevel[index]
                )
            }
        )

        return CheckoutResponse(status = "OK")
    }

    private fun filterRequiredCoupons(
        customerCoupons: Map<Coupon, Double>,
        requestCoupons: Array<RedeemedCoupon>
    ): Map<Coupon, Double> {
        val couponIds = requestCoupons.map(RedeemedCoupon::id)

        val couponEntriesToUpdate = customerCoupons.filter {
            couponIds.contains(it.key.id)
        }

        if (couponEntriesToUpdate.size != couponIds.size) {
            throw IllegalArgumentException("Some coupons are not the customer's coupon map")
        }

        if (couponEntriesToUpdate.any { !it.isRedeemable() }) {
            throw IllegalArgumentException("Some coupons are not redeemable!")
        }

        return couponEntriesToUpdate
    }

    private fun processRedeemedCoupons(
        customerCoupons: CustomerCoupons,
        redeemedCoupons: Map<Coupon, Double>
    ) {
        // subtract required amount of progress
        val updatedEntries = redeemedCoupons.mapValues {
            val level = it.getRedeemLevel()
            val (key, value) = it
            val threshold = key.rewards[level].threshold
            (value - threshold)
        }

        // update the customer's entries
        customerCoupons.putAll(updatedEntries)

        // when the gathered progress is 0 -> remove coupon from map
        customerCoupons.values.removeIf { it == .0 } // maybe it <= .0
    }

    private fun processPurchasedProducts(
        products: Array<PurchasedProduct>,
        customerCoupons: CustomerCoupons
    ) {
        // for each purchased product, update each relevant coupons' progress accordingly
        // also handle the case when the customer hasn't got the coupon in their list
        // in that case, first add it
        products.forEach { purchasedProduct ->
            val product = productService.getProduct(purchasedProduct.id)
            val amount = purchasedProduct.amount
            val coupons = couponService.getRelevantCouponsFor(product)

            coupons.forEach { coupon ->
                // find out how much this product is worth in this coupon
                val productProgress = coupon.getProgressFor(product)

                // then add it to the already existing item or add it as a new entry
                val oldProgress = customerCoupons.getOrPut(coupon) { .0 }
                val newProgress = oldProgress + productProgress * amount
                customerCoupons[coupon] = newProgress
            }
        }
    }
}