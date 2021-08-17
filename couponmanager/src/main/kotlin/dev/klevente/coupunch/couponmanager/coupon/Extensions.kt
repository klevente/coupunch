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

fun Reward.getMergedProducts(): Map<Product, Int> {
    val res = mutableMapOf<Product, Int>()
    productGroups.entries.forEach { (key, value) ->
        key.products.forEach { product -> res[product] = value }
    }
    res.putAll(products)
    return res
}