package dev.klevente.coupunch.analytics.config

import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class InfluxCreator(
    @Value("\${influx.url}") private val url: String,
    @Value("\${influx.token}") private val token: String,
    @Value("\${influx.organization}") private val organization: String,
    @Value("\${influx.bucket}") private val bucket: String
) {
    fun influx() = InfluxDBClientKotlinFactory.create(
        url = url,
        token = token.toCharArray(),
        org = organization,
        bucket = bucket
    )
}