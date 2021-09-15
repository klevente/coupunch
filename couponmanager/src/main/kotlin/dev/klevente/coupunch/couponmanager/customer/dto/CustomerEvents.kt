package dev.klevente.coupunch.couponmanager.customer.dto

class UserUpdateEvent(
    val id: Long,
    val username: String,
    val name: String,
    val code: String
)

class UserAddEvent(
    val companyId: String,
    val userId: Long
)