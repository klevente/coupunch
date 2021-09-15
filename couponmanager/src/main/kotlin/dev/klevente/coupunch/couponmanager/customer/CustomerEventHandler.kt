package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.UserUpdateEvent
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class CustomerEventHandler(
    private val customerEvents: CustomerEvents
) {
    @RabbitListener(queues = ["\${amqp.queue.couponmanager}"])
    fun handleUserUpdate(event: UserUpdateEvent) {
        try {
            customerEvents.update(event)
        } catch (e: Exception) {
            throw AmqpRejectAndDontRequeueException(e)
        }
    }
}