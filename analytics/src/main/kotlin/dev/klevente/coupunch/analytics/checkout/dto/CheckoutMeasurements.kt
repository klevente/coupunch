package dev.klevente.coupunch.analytics.checkout.dto

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.time.Instant

@Measurement(name = "purchase")
data class Purchase(
    @Column(timestamp = true) val time: Instant,
    @Column(tag = true) val userId: Long,
    @Column(tag = true) val product: String,
    @Column val amount: Double,
    @Column val price: Double,
)

@Measurement(name = "redeem")
data class Redeem(
    @Column(timestamp = true) val time: Instant,
    @Column(tag = true) val userId: Long,
    @Column(tag = true) val coupon: String,
    @Column(tag = true) val redeemLevel: Int,
    @Column(tag = true) val product: String,
    @Column val amount: Double,
    @Column val price: Double,
)