package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.toUpdateEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UserEventPublisher(
    private val amqpTemplate: AmqpTemplate,
    @Value("\${amqp.exchange.user}") private val userTopicExchangeName: String
) {
    fun updateUserInCompanies(user: User) {
        val event = user.toUpdateEvent()
        amqpTemplate.convertAndSend(userTopicExchangeName, "user.update", event)
    }
}