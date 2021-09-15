package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.config.CompanyConfigService
import dev.klevente.coupunch.couponmanager.customer.dto.toAddedEvent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CustomerEventPublisher(
    private val amqpTemplate: AmqpTemplate,
    @Value("\${amqp.exchange.customer}") private val customerTopicExchangeName: String,
    private val companyConfigService: CompanyConfigService,
) {
    fun customerAddedToCompany(customer: Customer) {
        val event = customer.toAddedEvent(companyConfigService.getCompanyId())
        amqpTemplate.convertAndSend(customerTopicExchangeName, "customer.add", event)
    }
}