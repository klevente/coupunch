package dev.klevente.coupunch.analytics.checkout.dto

import dev.klevente.coupunch.analytics.checkout.Purchase
import dev.klevente.coupunch.analytics.checkout.Redeem
import java.time.LocalDateTime

fun CheckoutEvent.toDomain(): Pair<List<Purchase>, List<Redeem>> {
    val purchases = products.map { it.toDomain(timestamp, userId) }
    val redeems = rewards.map { it.toDomain(timestamp, userId) }
    return purchases to redeems
}

fun PurchasedProductEvent.toDomain(time: LocalDateTime, userId: Long) = Purchase(
    time = time,
    userId = userId,
    product = name,
    amount = amount,
    price = price
)

fun RedeemedRewardEvent.toDomain(time: LocalDateTime, userId: Long) = Redeem(
    time = time,
    userId = userId,
    coupon = couponName,
    redeemLevel = redeemLevel,
    product = name,
    amount = amount,
    price = price
)