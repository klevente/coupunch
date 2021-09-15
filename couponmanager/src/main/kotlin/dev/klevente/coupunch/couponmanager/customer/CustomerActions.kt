package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.*

interface CustomerActions {
    fun getCompanyCustomers(): CustomersResponse

    fun addCustomer(request: CustomerCreateRequest): CustomerResponse

    fun updateCustomer(id: Long, request: CustomerUpdateRequest): CustomerResponse

    fun getCustomerByCode(code: String): CustomerResponse

    fun getCouponsForCustomer(username: String): CustomerCouponsResponse

    fun getCouponsForUser(userId: Long): UserCouponsResponse

    fun getCouponForUser(userId: Long, couponId: Long): UserCouponResponse

    fun resendUserAddedToCompanyEvent(userId: Long)
}