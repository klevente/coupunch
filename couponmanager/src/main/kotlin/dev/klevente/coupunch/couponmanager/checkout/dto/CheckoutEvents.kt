package dev.klevente.coupunch.couponmanager.checkout.dto

import java.time.LocalDateTime

class CheckoutEvent(
    val timestamp: LocalDateTime,
    val userId: Long,
    val products: Array<PurchasedProductEvent>,
    val rewards: Array<RedeemedRewardEvent>
)

class PurchasedProductEvent(
    val name: String,
    val amount: Int,
    val price: Double
)

class RedeemedRewardEvent(
    val name: String,
    val amount: Int,
    val price: Double,
    val couponName: String,
    val redeemLevel: Int
)