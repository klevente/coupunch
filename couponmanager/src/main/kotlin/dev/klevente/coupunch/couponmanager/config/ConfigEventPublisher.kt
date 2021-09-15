package dev.klevente.coupunch.couponmanager.config

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

class CompanyUpdateEvent(
    val id: String,
    val name: String,
    val url: String
)

@Service
class ConfigEventPublisher(
    private val amqpTemplate: AmqpTemplate,
    @Value("\${amqp.exchange.company}") private val companyTopicExchangeName: String,
    private val companyConfigService: CompanyConfigService,
) {
    fun companyInformationUpdated() {
        val event = buildEvent()
        amqpTemplate.convertAndSend(companyTopicExchangeName, "company.update", event)
    }

    private fun buildEvent() = CompanyUpdateEvent(
        id = companyConfigService.getCompanyId(),
        name = companyConfigService.getCompanyName(),
        url = companyConfigService.getCompanyUrl()
    )
}