package dev.klevente.coupunch.couponmanager.coupon.dto

import java.math.BigDecimal

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
    val price: BigDecimal,
    val points: Int?
)

class EligibleProductGroupResponse(
    val id: Long,
    val name: String,
    val points: Int?
)

class RewardResponse(
    val threshold: BigDecimal,
    val discountType: String,
    val discount: BigDecimal,
    val products: Array<ProductRewardResponse>,
    val productGroups: Array<ProductGroupRewardResponse>
)

class ProductRewardResponse(
    val id: Long,
    val name: String,
    val amount: Int,
    val originalPrice: BigDecimal
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
    val progress: BigDecimal,
    val redeemable: Boolean,
    val redeemLevel: Int,
    val rewards: Array<RewardRedeemResponse>
)

class RewardRedeemResponse(
    val threshold: BigDecimal,
    val discount: BigDecimal,
    val discountType: String,
    val products: Array<ProductRewardResponse>
)