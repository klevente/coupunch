package dev.klevente.coupunch.couponmanager.checkout

import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("checkout")
class CheckoutController(
    private val checkoutService: CheckoutService
) {
    @PostMapping("{username}")
    fun checkout(
        @PathVariable username: String,
        @RequestBody request: CheckoutRequest
    ) = checkoutService.checkout(username, request)
}