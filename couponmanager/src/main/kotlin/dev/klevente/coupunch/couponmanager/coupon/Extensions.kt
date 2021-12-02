package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.product.Product
import java.math.BigDecimal

fun Coupon.hasMoreEligibleProductsOrAnyProductGroups() = eligibleProducts.size > 1 || eligibleProductGroups.isNotEmpty()

fun Coupon.hasMoreEligibleProductGroupsOrAnyProducts() = eligibleProductGroups.size > 1 || eligibleProducts.isNotEmpty()

fun Reward.hasMoreRewardProductsOrAnyProductGroups() = products.size > 1 || productGroups.isNotEmpty()

fun Reward.hasMoreRewardProductGroupsOrAnyProducts() = productGroups.size > 1 || products.isNotEmpty()

fun Coupon.isRedeemable(progress: BigDecimal) = getRedeemLevel(progress) > -1

fun Coupon.getRedeemLevel(progress: BigDecimal) = rewards.fold(-1) { acc, reward ->
    if (progress >= reward.threshold) acc + 1
    else acc
}

fun Coupon.getMergedEligibleProducts(): Map<Product, Int?> {
    val mergedProducts = mutableMapOf<Product, Int>()
    eligibleProductGroups.entries.forEach { (key, value) ->
        key.products.forEach { mergedProducts[it] = value }
    }
    mergedProducts.putAll(eligibleProducts)
    return mergedProducts
}

fun Coupon.getProgressFor(product: Product) = when (type) {
    CouponType.POINT -> getMergedEligibleProducts()[product]!!.toBigDecimal()
    CouponType.PRICE -> product.price
}

fun Reward.getMergedProducts(): Map<Product, Int> {
    val mergedProducts = mutableMapOf<Product, Int>()
    productGroups.entries.forEach { (key, value) ->
        key.products.forEach { mergedProducts[it] = value }
    }
    mergedProducts.putAll(products)
    return mergedProducts
}

fun Map.Entry<Coupon, BigDecimal>.isRedeemable() = key.isRedeemable(value)

fun Map.Entry<Coupon, BigDecimal>.getRedeemLevel(): Int = key.getRedeemLevel(value)

fun Product.calculateDiscountedPrice(discountType: DiscountType, discount: BigDecimal) = when (discountType) {
    DiscountType.FIXED -> (price - discount).coerceAtLeast(0.0.toBigDecimal())
    DiscountType.PERCENTAGE -> (price * ((1).toBigDecimal() - discount / 100.0.toBigDecimal())).coerceAtLeast(0.0.toBigDecimal())
}