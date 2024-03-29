package dev.klevente.coupunch.analytics.checkout.dto

import java.time.LocalDateTime

class CheckoutEvent(
    val timestamp: LocalDateTime = LocalDateTime.MIN,
    val userId: Long = -1,
    val products: Array<PurchasedProductEvent> = emptyArray(),
    val rewards: Array<RedeemedRewardEvent> = emptyArray()
)

class PurchasedProductEvent(
    val name: String = "",
    val amount: Int = -1,
    val price: Double = -1.0
)

class RedeemedRewardEvent(
    val name: String = "",
    val amount: Int = -1,
    val price: Double = -1.0,
    val couponName: String = "",
    val redeemLevel: Int = -1
)