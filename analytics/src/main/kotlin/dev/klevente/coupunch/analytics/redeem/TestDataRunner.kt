package dev.klevente.coupunch.analytics.redeem

import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory
import com.influxdb.client.write.Point
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class TestDataRunner : ApplicationRunner {
    override fun run(args: ApplicationArguments) = runBlocking {
        val url = "http://localhost:8086"
        val token = "WPimOR45ouS27G15URLcJmgVtgtLcVKyAHApKWXfcQgYgv5X5qCO3uGrloascIUTR2Uk0Y7RtaExFwv70LHZrw=="
        val org = "couponmanager"
        val bucket = "analytics"
        val influx = InfluxDBClientKotlinFactory
            .create(url, token.toCharArray(), org, bucket)

        val write = influx.getWriteKotlinApi()

        val point = Point.measurement("redeem")
            .addTag("location", "west")
            .addField("value", 40)
            .time(Instant.now().toEpochMilli(), WritePrecision.MS)

        write.writePoint(point)
    }
}