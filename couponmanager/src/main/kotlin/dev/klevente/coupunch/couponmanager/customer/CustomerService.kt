package dev.klevente.coupunch.couponmanager.customer

interface CustomerService {
    fun getCustomer(id: Long): Customer

    fun getCustomerByUsername(username: String): Customer
}