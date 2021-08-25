package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.*

interface CustomerService {

    fun getCustomer(id: Long): Customer

    fun getCustomerByUsername(username: String): Customer

    fun getCompanyCustomers(): CustomersResponse

    fun addCustomer(request: CustomerCreateRequest): CustomerResponse

    fun updateCustomer(id: Long, request: CustomerUpdateRequest): CustomerResponse

    fun getCouponsForCustomer(username: String): CustomerCouponsResponse

    fun getCustomerFromQrCode(code: String): CustomerResponse
}