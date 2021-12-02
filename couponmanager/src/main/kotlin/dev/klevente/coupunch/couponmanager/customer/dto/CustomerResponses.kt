package dev.klevente.coupunch.couponmanager.customer.dto

import java.math.BigDecimal

class CustomersResponse(
    val customers: Array<CustomerResponse>
)

class CustomerResponse(
    val id: Long,
    val username: String,
    val name: String
)

class CustomerCouponsResponse(
    val coupons: Array<CustomerCouponResponse>
)

class CustomerCouponResponse(
    val id: Long,
    val name: String,
    val type: String,
    val progress: BigDecimal,
    val redeemable: Boolean,
    val redeemLevel: Int,
    val rewards: Array<CustomerCouponReward>
)

class CustomerCouponReward(
    val threshold: BigDecimal,
    val discountType: String,
    val discount: BigDecimal,
    val products: Array<CustomerCouponRewardProduct>
)

class CustomerCouponRewardProduct(
    val id: Long,
    val name: String,
    val amount: Int,
    val originalPrice: BigDecimal,
    val discountedPrice: BigDecimal
)

class UserCouponsResponse(
    val coupons: Array<UserCouponResponse>
)

class UserCouponResponse(
    val id: Long,
    val name: String,
    val type: String,
    val progress: BigDecimal,
    val redeemable: Boolean,
    val redeemLevel: Int,
    val eligibleItems: UserEligibleItems,
    val rewards: Array<UserCouponReward>
)

class UserEligibleItems(
    val products: Array<UserEligibleProduct>,
    val productGroups: Array<UserEligibleProductGroup>
)

class UserEligibleProduct(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val points: Int?
)

class UserEligibleProductGroup(
    val id: Long,
    val name: String,
    val points: Int?,
    val products: Array<UserEligibleProductGroupProduct>
)

class UserEligibleProductGroupProduct(
    val id: Long,
    val name: String,
    val price: BigDecimal
)

class UserCouponReward(
    val threshold: BigDecimal,
    val discountType: String,
    val discount: BigDecimal,
    val products: Array<UserRewardProduct>,
    val productGroups: Array<UserRewardProductGroup>,
    val mergedProducts: Array<UserRewardProduct>
)

class UserRewardProduct(
    val id: Long,
    val name: String,
    val amount: Int,
    val originalPrice: BigDecimal,
    val discountedPrice: BigDecimal
)

class UserRewardProductGroup(
    val id: Long,
    val name: String,
    val amount: Int,
    val products: Array<UserRewardProductGroupProduct>
)

class UserRewardProductGroupProduct(
    val id: Long,
    val name: String,
    val originalPrice: BigDecimal,
    val discountedPrice: BigDecimal
)