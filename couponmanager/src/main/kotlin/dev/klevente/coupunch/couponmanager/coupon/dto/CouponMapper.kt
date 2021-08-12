package dev.klevente.coupunch.couponmanager.coupon.dto

import dev.klevente.coupunch.couponmanager.coupon.*
import dev.klevente.coupunch.library.util.mapToArray

fun Collection<Coupon>.toResponse() = CouponsResponse(
    coupons = mapToArray { it.toResponse() }
)

fun Coupon.toResponse() = CouponResponse(
    id = id,
    name = name,
    eligibleItems = EligibleItemsResponse(
        products = eligibleProducts.toResponse(),
        productGroups = eligibleProductGroups.toResponse()
    ),
    type = type.toString(),
    rewards = rewards.toResponse()
)

fun EligibleProducts.toResponse() = mapToArray { (key, value) ->
    EligibleProductResponse(
        id = key.id,
        name = key.name,
        price = key.price,
        points = value
    )
}

fun EligibleProductGroups.toResponse() = mapToArray { (key, value) ->
    EligibleProductGroupResponse(
        id = key.id,
        name = key.name,
        points = value
    )
}

fun Collection<Reward>.toResponse() = mapToArray { it.toResponse() }

fun Reward.toResponse() = RewardResponse(
    threshold = threshold,
    discountType = discountType.toString(),
    discount = discount,
    products = products.toResponse(),
    productGroups = productGroups.toResponse()
)

fun ProductRewards.toResponse() = mapToArray { (key, value) ->
    ProductRewardResponse(
        id = key.id,
        name = key.name,
        originalPrice = key.price,
        amount = value
    )
}

fun ProductGroupRewards.toResponse() = mapToArray { (key, value) ->
    ProductGroupRewardResponse(
        id = key.id,
        name = key.name,
        amount = value
    )
}
