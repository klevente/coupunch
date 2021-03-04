package dev.klevente.coupunch.couponmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class CouponManagerApplication

fun main(args: Array<String>) {
	runApplication<CouponManagerApplication>(*args)
}

@RestController
@RequestMapping("/coupons")
class TestController {

	@GetMapping
	fun getCoupons() = ok("Coupon 1: 1, Coupon 2: 2")

	@GetMapping("{id}")
	fun getCoupon(@PathVariable id: String) = ok("Coupon $id: $id")
}
