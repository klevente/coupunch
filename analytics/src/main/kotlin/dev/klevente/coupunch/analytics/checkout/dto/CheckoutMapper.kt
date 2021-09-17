package dev.klevente.coupunch.analytics.checkout.dto

import dev.klevente.coupunch.analytics.checkout.Purchase
import dev.klevente.coupunch.analytics.checkout.Redeem
import java.time.Instant
import java.time.LocalDateTime

/*fun CheckoutEvent.toMeasurements(instant: Instant): Pair<List<Purchase>, List<Redeem>> {
    val purchaseMeasurements = products.map { it.toMeasurement(instant, userId) }
    val redeemMeasurements = rewards.map { it.toMeasurement(instant, userId) }
    return purchaseMeasurements to redeemMeasurements
}

fun PurchasedProductEvent.toMeasurement(instant: Instant, userId: Long) = Purchase(
    time = instant,
    product = name,
    amount = amount.toDouble(),
    price = price,
    userId = userId
)

fun RedeemedRewardEvent.toMeasurement(instant: Instant, userId: Long) = Redeem(
    time = instant,
    coupon = couponName,
    redeemLevel = redeemLevel,
    product = name,
    amount = amount.toDouble(),
    price = price,
    userId = userId
)*/

fun CheckoutEvent.toDomain(time: LocalDateTime): Pair<List<Purchase>, List<Redeem>> {
    val purchases = products.map { it.toDomain(time, userId) }
    val redeems = rewards.map { it.toDomain(time, userId) }
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