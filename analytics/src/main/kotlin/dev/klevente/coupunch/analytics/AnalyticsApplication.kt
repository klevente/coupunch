package dev.klevente.coupunch.analytics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["dev.klevente.coupunch"])
class AnalyticsApplication

fun main(args: Array<String>) {
	runApplication<AnalyticsApplication>(*args)
}
