package dev.klevente.coupunch.couponmanager.checkout

import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutRequest
import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutResponse

interface CheckoutService {

    fun checkout(request: CheckoutRequest): CheckoutResponse
}