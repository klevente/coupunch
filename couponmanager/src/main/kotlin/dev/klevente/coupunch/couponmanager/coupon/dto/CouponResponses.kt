package dev.klevente.coupunch.couponmanager.coupon.dto

class CouponsResponse(
    val coupons: Array<CouponResponse>
)

class CouponResponse(
    val id: Long,
    val name: String,
    val type: String,
    val eligibleItems: EligibleItemsResponse,
    val rewards: Array<RewardResponse>
)

class EligibleItemsResponse(
    val products: Array<EligibleProductResponse>,
    val productGroups: Array<EligibleProductGroupResponse>
)

class EligibleProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val points: Int?
)

class EligibleProductGroupResponse(
    val id: Long,
    val name: String,
    val points: Int?
)

class RewardResponse(
    val threshold: Double,
    val discountType: String,
    val discount: Double,
    val products: Array<ProductRewardResponse>,
    val productGroups: Array<ProductGroupRewardResponse>
)

class ProductRewardResponse(
    val id: Long,
    val name: String,
    val amount: Int,
    val originalPrice: Double
)

class ProductGroupRewardResponse(
    val id: Long,
    val name: String,
    val amount: Int
)

class CouponRedeemResponse(
    val id: Long,
    val name: String,
    val type: String,
    val progress: Double,
    val redeemable: Boolean,
    val redeemLevel: Int,
    val rewards: Array<RewardRedeemResponse>
)

class RewardRedeemResponse(
    val threshold: Double,
    val discount: Double,
    val discountType: String,
    val products: Array<ProductRewardResponse>
)