package dev.klevente.coupunch.couponmanager.customer.dto

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
    val progress: Double,
    val redeemable: Boolean,
    val redeemLevel: Int,
    val rewards: Array<CustomerCouponReward>
)

class CustomerCouponReward(
    val threshold: Double,
    val discountType: String,
    val discount: Double,
    val products: Array<CustomerCouponRewardProduct>
)

class CustomerCouponRewardProduct(
    val id: Long,
    val name: String,
    val amount: Int,
    val originalPrice: Double,
    val discountedPrice: Double
)