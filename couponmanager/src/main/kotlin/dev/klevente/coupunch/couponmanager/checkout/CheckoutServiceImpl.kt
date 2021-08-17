package dev.klevente.coupunch.couponmanager.checkout

import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutRequest
import dev.klevente.coupunch.couponmanager.checkout.dto.CheckoutResponse
import dev.klevente.coupunch.couponmanager.customer.CustomerService
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CheckoutServiceImpl(
    private val log: Logger,
    private val customerService: CustomerService
) : CheckoutService {

    @Transactional
    override fun checkout(username: String, request: CheckoutRequest): CheckoutResponse {
        val customer = customerService.getCustomerByUsername(username)

        // gather coupons that need to be redeemed -> subtract required amount of progress
        // when the gathered progress is 0 -> remove coupon from map

        // for each purchased product, update coupons's standings accordingly
        // also handle the case when the customer hasn't got the coupon in their list
        // in that case, first add it

        return CheckoutResponse(status = "OK")
    }
}