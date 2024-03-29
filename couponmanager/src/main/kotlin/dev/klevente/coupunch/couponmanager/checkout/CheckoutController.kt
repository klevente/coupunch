package dev.klevente.coupunch.couponmanager.checkout

import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("checkout")
class CheckoutController(
    private val checkoutActions: CheckoutActions
) {
    @PostMapping("{username}")
    fun checkout(
        @PathVariable username: String,
        @RequestBody @Valid request: CheckoutRequest
    ) = ok(checkoutActions.checkout(username, request))
}