package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.product.Product

fun Coupon.hasMoreEligibleProductsOrAnyProductGroups() =
    eligibleProducts.size > 1 || eligibleProductGroups.isNotEmpty()

fun Coupon.hasMoreEligibleProductGroupsOrAnyProducts() =
    eligibleProductGroups.size > 1 || eligibleProducts.isNotEmpty()

fun Reward.hasMoreRewardProductsOrAnyProductGroups() =
    products.size > 1 || productGroups.isNotEmpty()

fun Reward.hasMoreRewardProductGroupsOrAnyProducts() =
    productGroups.size > 1 || products.isNotEmpty()

fun Coupon.isRedeemable(currentStanding: Double) = getRedeemLevel(currentStanding) > -1

fun Coupon.getRedeemLevel(currentStanding: Double): Int {
    var level = -1
    rewards.forEachIndexed { index, reward ->
        if (currentStanding >= reward.threshold) {
            level = index
        }
    }
    return level
}

fun Coupon.getMergedEligibleProducts(): Map<Product, Int?> {
    val res = mutableMapOf<Product, Int?>()
    eligibleProductGroups.entries.forEach { (key, value) ->
        key.products.forEach { product -> res[product] = value }
    }
    res.putAll(eligibleProducts)
    return res
}

fun Coupon.getProgressFor(product: Product): Double {
    return when (type) {
        CouponType.POINT -> {
           getMergedEligibleProducts()[product]!!.toDouble()
        }
        CouponType.PRICE -> product.price
    }
}

fun Reward.getMergedProducts(): Map<Product, Int> {
    val res = mutableMapOf<Product, Int>()
    productGroups.entries.forEach { (key, value) ->
        key.products.forEach { product -> res[product] = value }
    }
    res.putAll(products)
    return res
}

fun Map.Entry<Coupon, Double>.isRedeemable() = key.isRedeemable(value)

fun Map.Entry<Coupon, Double>.getRedeemLevel(): Int = key.getRedeemLevel(value)