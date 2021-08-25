package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.CustomerCreateRequest
import dev.klevente.coupunch.couponmanager.customer.dto.CustomerUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerService: CustomerService
) {
    @GetMapping
    fun getCustomers() = customerService.getCompanyCustomers()

    @PostMapping
    fun addCustomer(
        @RequestBody request: CustomerCreateRequest
    ) = customerService.addCustomer(request)

    @PutMapping("{customerId}")
    fun updateCustomer(
        @PathVariable customerId: Long,
        @RequestBody request: CustomerUpdateRequest
    ) = customerService.updateCustomer(customerId, request)

    @GetMapping("qr/{code}")
    fun getCustomerFromQrCode(
        @PathVariable code: String
    ) = customerService.getCustomerFromQrCode(code)

    @GetMapping("{username}/coupons")
    fun getCustomerCoupons(
        @PathVariable username: String
    ) = customerService.getCouponsForCustomer(username)
}