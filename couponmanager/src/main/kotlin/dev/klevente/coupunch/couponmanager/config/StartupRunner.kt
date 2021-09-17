package dev.klevente.coupunch.couponmanager.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StartupRunner(
    private val configRepository: ConfigRepository,
    private val configEventPublisher: ConfigEventPublisher,
    @Value("\${spring.application.name}") private val companyId: String,
) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments) {
        if (configRepository.count() == 0L) {
            initConfigData()
        }
        configEventPublisher.companyInformationUpdated()
    }

    private fun initConfigData() {
        val url = ConfigEntry.create("url", companyId)
        val name = ConfigEntry.create("name", "Coupon Manager")
        val currency = ConfigEntry.create("currency", "USD")

        configRepository.saveAll(listOf(url, name, currency))
    }
}