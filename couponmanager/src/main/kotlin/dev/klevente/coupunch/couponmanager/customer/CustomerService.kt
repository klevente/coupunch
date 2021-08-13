package dev.klevente.coupunch.couponmanager.customer

interface CustomerService {

    fun getCompanyCustomers()

    fun addCustomer()

    fun getCouponsForCustomer(username: String)

    fun getCustomerFromQrCode(code: String)
}