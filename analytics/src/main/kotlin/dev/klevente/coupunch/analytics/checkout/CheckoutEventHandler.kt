package dev.klevente.coupunch.analytics.checkout

import dev.klevente.coupunch.analytics.checkout.dto.CheckoutEvent
import dev.klevente.coupunch.analytics.checkout.dto.toDomain
import org.slf4j.Logger
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = false)
class CheckoutEventHandler(
    private val log: Logger,
    private val purchaseRepository: PurchaseRepository,
    private val redeemRepository: RedeemRepository
) {
    @RabbitListener(queues = ["\${amqp.queue.checkout}"])
    @Transactional
    fun checkout(event: CheckoutEvent) {
        try {
            insertIntoDb(event)
        } catch (e: Exception) {
            throw AmqpRejectAndDontRequeueException(e)
        }
    }

    private fun insertIntoDb(event: CheckoutEvent) {
        val now = LocalDateTime.now()
        val pair = event.toDomain(now)
        purchaseRepository.saveAll(pair.first)
        redeemRepository.saveAll(pair.second)
    }
    /*private fun insertToInflux(event: CheckoutEvent) = runBlocking {
        val now = Instant.now()
        val (first, second) = event.toMeasurements(now)

        influxCreator.influx().use {
            val write = it.getWriteKotlinApi()
            write.writeMeasurements(first, precision = WritePrecision.MS)
            write.writeMeasurements(second, precision = WritePrecision.MS)
        }
    }*/

}



