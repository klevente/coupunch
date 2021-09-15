package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.UserAddEvent
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class UserEventHandler(
    private val userEvents: UserEvents
) {
    @RabbitListener(queues = ["\${amqp.queue.customer}"])
    fun handleCompanyAddedForUser(event: UserAddEvent) {
        try {
            userEvents.addCompanyToUsersList(event)
        } catch (e: Exception) {
            throw AmqpRejectAndDontRequeueException(e)
        }
    }
}