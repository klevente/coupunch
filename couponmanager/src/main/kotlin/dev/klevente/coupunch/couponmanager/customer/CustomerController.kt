package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.CustomerCreateRequest
import dev.klevente.coupunch.couponmanager.customer.dto.CustomerUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerActions: CustomerActions
) {
    @GetMapping
    fun getCustomers() = customerActions.getCompanyCustomers()

    @PostMapping
    fun addCustomer(
        @RequestBody request: CustomerCreateRequest
    ) = customerActions.addCustomer(request)

    @PutMapping("{customerId}")
    fun updateCustomer(
        @PathVariable customerId: Long,
        @RequestBody request: CustomerUpdateRequest
    ) = customerActions.updateCustomer(customerId, request)

    @GetMapping("qr/{code}")
    fun getCustomerFromQrCode(
        @PathVariable code: String
    ) = customerActions.getCustomerFromQrCode(code)

    @GetMapping("{username}/coupons")
    fun getCustomerCoupons(
        @PathVariable username: String
    ) = customerActions.getCouponsForCustomer(username)
}