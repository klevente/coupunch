package dev.klevente.coupunch.couponmanager.checkout

import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutRequest
import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CheckoutServiceImpl : CheckoutService {

    override fun checkout(username: String, request: CheckoutRequest): CheckoutResponse {
        TODO("Not yet implemented")
    }
}