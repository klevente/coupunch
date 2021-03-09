package dev.klevente.coupunch.couponmanager

import dev.klevente.coupunch.library.security.AuthenticationFacade
import org.slf4j.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(scanBasePackages = ["dev.klevente.coupunch"])
class CouponManagerApplication

fun main(args: Array<String>) {
    runApplication<CouponManagerApplication>(*args)
}

@RestController
@RequestMapping("/coupons")
class TestController(
    private val authenticationFacade: AuthenticationFacade,
    private val logger: Logger
) {

    @GetMapping
    fun getCoupons(): ResponseEntity<Coupons> {
        return ok(Coupons(arrayOf(Coupon(1, "Coupon 1"), Coupon(2, "Coupon 2"))))
    }

    @GetMapping("{id}")
    fun getCoupon(@PathVariable id: String): ResponseEntity<Coupon> {
        logger.info(authenticationFacade.authInfo)
        return ok(Coupon(id.toInt(), "Coupon $id"))
    }
}

class Coupon(
    val id: Int,
    val name: String
)

class Coupons(
    val coupons: Array<Coupon>
)
