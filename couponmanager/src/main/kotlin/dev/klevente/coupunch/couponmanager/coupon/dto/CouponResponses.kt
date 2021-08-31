package dev.klevente.coupunch.couponmanager.coupon.dto

class CouponsResponse(
    val coupons: Array<CouponResponse>
)

class CouponResponse(
    val id: Long,
    val name: String,
    val type: String,
    val eligibleItems: EligibleItemsResponse,
    rewards: Array<RewardResponse>
)

class EligibleItemsResponse(
    products: Array<EligibleProductResponse>,
    productGroups: Array<EligibleProductGroupResponse>
)

class EligibleProductResponse(
    id: Long,
    name: String,
    price: Double,
    points: Int?
)

class EligibleProductGroupResponse(
    id: Long,
    name: String,
    points: Int?
)

class RewardResponse(
    threshold: Double,
    discountType: String,
    discount: Double,
    products: Array<ProductRewardResponse>,
    productGroups: Array<ProductGroupRewardResponse>
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