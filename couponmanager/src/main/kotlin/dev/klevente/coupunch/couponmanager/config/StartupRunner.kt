package dev.klevente.coupunch.couponmanager.config

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class StartupRunner(
    private val configEventPublisher: ConfigEventPublisher
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        configEventPublisher.companyInformationUpdated()
    }
}