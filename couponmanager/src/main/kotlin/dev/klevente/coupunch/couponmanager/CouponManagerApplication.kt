package dev.klevente.coupunch.couponmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["dev.klevente.coupunch"])
class CouponManagerApplication

fun main(args: Array<String>) {
    runApplication<CouponManagerApplication>(*args)
}
