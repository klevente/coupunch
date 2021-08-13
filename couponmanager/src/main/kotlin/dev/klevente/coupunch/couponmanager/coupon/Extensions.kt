package dev.klevente.coupunch.couponmanager.coupon

fun Coupon.hasMoreEligibleProductsOrAnyProductGroups() =
    eligibleProducts.size > 1 || eligibleProductGroups.isNotEmpty()

fun Coupon.hasMoreEligibleProductGroupsOrAnyProducts() =
    eligibleProductGroups.size > 1 || eligibleProducts.isNotEmpty()

fun Reward.hasMoreRewardProductsOrAnyProductGroups() =
    products.size > 1 || productGroups.isNotEmpty()

fun Reward.hasMoreRewardProductGroupsOrAnyProducts() =
    productGroups.size > 1 || products.isNotEmpty()