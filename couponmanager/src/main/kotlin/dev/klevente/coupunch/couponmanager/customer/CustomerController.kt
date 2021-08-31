package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.CustomerCreateRequest
import dev.klevente.coupunch.couponmanager.customer.dto.CustomerUpdateRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerActions: CustomerActions
) {
    @GetMapping
    fun getCustomers() = ok(customerActions.getCompanyCustomers())

    @PostMapping
    fun addCustomer(
        @RequestBody request: CustomerCreateRequest
    ) = ok(customerActions.addCustomer(request))

    @PutMapping("{customerId}")
    fun updateCustomer(
        @PathVariable customerId: Long,
        @RequestBody request: CustomerUpdateRequest
    ) = ok(customerActions.updateCustomer(customerId, request))

    @GetMapping("qr/{code}")
    fun getCustomerFromQrCode(
        @PathVariable code: String
    ) = ok(customerActions.getCustomerFromQrCode(code))

    @GetMapping("{username}/coupons")
    fun getCustomerCoupons(
        @PathVariable username: String
    ) = ok(customerActions.getCouponsForCustomer(username))
}