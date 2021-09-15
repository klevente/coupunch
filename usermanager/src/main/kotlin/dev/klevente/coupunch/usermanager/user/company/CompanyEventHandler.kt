package dev.klevente.coupunch.usermanager.user.company

import dev.klevente.coupunch.usermanager.user.company.dto.CompanyUpdateEvent
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class CompanyEventHandler(
    private val companyEvents: CompanyEvents
) {
    @RabbitListener(queues = ["\${amqp.queue.company}"])
    fun handleCompanyUpdate(event: CompanyUpdateEvent) {
        try {
            companyEvents.updateOrAddCompany(event)
        } catch (e: Exception) {
            throw AmqpRejectAndDontRequeueException(e)
        }
    }
}