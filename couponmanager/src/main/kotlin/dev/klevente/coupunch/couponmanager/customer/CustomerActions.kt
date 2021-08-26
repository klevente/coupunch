package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.*

interface CustomerActions {
    fun getCompanyCustomers(): CustomersResponse

    fun addCustomer(request: CustomerCreateRequest): CustomerResponse

    fun updateCustomer(id: Long, request: CustomerUpdateRequest): CustomerResponse

    fun getCustomerFromQrCode(code: String): CustomerResponse

    fun getCouponsForCustomer(username: String): CustomerCouponsResponse
}