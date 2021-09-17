package dev.klevente.coupunch.analytics.checkout.dto

import java.time.Instant

fun CheckoutEvent.toMeasurements(instant: Instant): Pair<List<Purchase>, List<Redeem>> {
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
)