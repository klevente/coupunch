package dev.klevente.coupunch.analytics.checkout

import com.influxdb.client.domain.WritePrecision
import dev.klevente.coupunch.analytics.checkout.dto.CheckoutEvent
import dev.klevente.coupunch.analytics.checkout.dto.toMeasurements
import dev.klevente.coupunch.analytics.config.InfluxCreator
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CheckoutEventHandler(
    private val log: Logger,
    private val influxCreator: InfluxCreator
) {
    @RabbitListener(queues = ["\${amqp.queue.checkout}"])
    fun checkout(event: CheckoutEvent) {
        try {
            insertToInflux(event)
        } catch (e: Exception) {
            throw AmqpRejectAndDontRequeueException(e)
        }
    }

    private fun insertToInflux(event: CheckoutEvent) = runBlocking {
        val now = Instant.now()
        val (first, second) = event.toMeasurements(now)

        influxCreator.influx().use {
            val write = it.getWriteKotlinApi()
            write.writeMeasurements(first, precision = WritePrecision.MS)
            write.writeMeasurements(second, precision = WritePrecision.MS)
        }
    }
}



