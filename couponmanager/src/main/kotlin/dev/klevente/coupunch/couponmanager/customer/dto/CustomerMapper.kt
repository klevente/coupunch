package dev.klevente.coupunch.couponmanager.customer.dto

import dev.klevente.coupunch.couponmanager.coupon.*
import dev.klevente.coupunch.couponmanager.coupon.dto.toPointsResponse
import dev.klevente.coupunch.couponmanager.customer.Customer
import dev.klevente.coupunch.couponmanager.customer.CustomerCoupons
import dev.klevente.coupunch.library.util.mapToArray

fun Collection<Customer>.toUserResponse() = CustomersResponse(
    customers = mapToArray(Customer::toUserResponse)
)

fun Customer.toUserResponse() = CustomerResponse(
    id = id,
    username = username,
    name = name
)

fun CustomerCoupons.toResponse() = CustomerCouponsResponse(
    coupons = mapToArray { (key, value) ->
        CustomerCouponResponse(
            id = key.id,
            name = key.name,
            type = key.type.toString(),
            progress = value,
            redeemable = key.isRedeemable(value),
            redeemLevel = key.getRedeemLevel(value),
            rewards = key.rewards.mapToArray(Reward::toResponse)
        )
    }
)

fun Reward.toResponse() = CustomerCouponReward(
    threshold = threshold,
    discountType = discountType.toString(),
    discount = discount,
    products = getMergedProducts().mapToArray { (key, value) ->
        CustomerCouponRewardProduct(
            id = key.id,
            name = key.name,
            amount = value,
            originalPrice = key.price,
            discountedPrice = key.calculateDiscountedPrice(discountType, discount)
        )
    }
)

fun CustomerCoupons.toUserResponse() = UserCouponsResponse(
    coupons = mapToArray(Map.Entry<Coupon, Double>::toUserResponse)
)

fun Map.Entry<Coupon, Double>.toUserResponse() = UserCouponResponse(
    id = key.id,
    name = key.name,
    type = key.type.toString(),
    progress = value,
    redeemable = key.isRedeemable(value),
    redeemLevel = key.getRedeemLevel(value),
    eligibleItems = UserEligibleItems(
        products = key.eligibleProducts.toUserEligibleResponse(),
        productGroups = key.eligibleProductGroups.toUserEligibleResponse()
    ),
    rewards = key.rewards.mapToArray(Reward::toUserResponse)
)

fun EligibleProducts.toUserEligibleResponse() = mapToArray { (key, value) ->
    UserEligibleProduct(
        id = key.id,
        name = key.name,
        price = key.price,
        points = value.toPointsResponse()
    )
}

fun EligibleProductGroups.toUserEligibleResponse() = mapToArray { (key, value) ->
    UserEligibleProductGroup(
        id = key.id,
        name = key.name,
        points = value.toPointsResponse(),
        products = key.products.mapToArray {
            UserEligibleProductGroupProduct(
                id = it.id,
                name = it.name,
                price = it.price
            )
        }
    )
}

fun Reward.toUserResponse() = UserCouponReward(
    threshold = threshold,
    discountType = discountType.toString(),
    discount = discount,
    products = products.mapToArray { (key, value) ->
        UserRewardProduct(
            id = key.id,
            name = key.name,
            amount = value,
            originalPrice = key.price,
            discountedPrice = key.calculateDiscountedPrice(discountType, discount)
        )
    },
    productGroups = productGroups.mapToArray { (key, value) ->
        UserRewardProductGroup(
            id = key.id,
            name = key.name,
            amount = value,
            products = key.products.mapToArray {
                UserRewardProductGroupProduct(
                    id = it.id,
                    name = it.name,
                    originalPrice = it.price,
                    discountedPrice = it.calculateDiscountedPrice(discountType, discount)
                )
            }
        )
    },
    mergedProducts = getMergedProducts().mapToArray { (key, value) ->
        UserRewardProduct(
            id = key.id,
            name = key.name,
            amount = value,
            originalPrice = key.price,
            discountedPrice = key.calculateDiscountedPrice(discountType, discount)
        )
    }
)