package dev.klevente.coupunch.couponmanager.customer.dto

import dev.klevente.coupunch.couponmanager.coupon.Reward
import dev.klevente.coupunch.couponmanager.coupon.getMergedProducts
import dev.klevente.coupunch.couponmanager.coupon.getRedeemLevel
import dev.klevente.coupunch.couponmanager.coupon.isRedeemable
import dev.klevente.coupunch.couponmanager.customer.Customer
import dev.klevente.coupunch.couponmanager.customer.CustomerCoupons
import dev.klevente.coupunch.library.util.mapToArray

fun Collection<Customer>.toResponse() = CustomersResponse(
    customers = mapToArray(Customer::toResponse)
)

fun Customer.toResponse() = CustomerResponse(
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
            rewards = key.rewards.mapToArray { it.toResponse() }
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
            discountedPrice = key.price // TODO
        )
    }
)