package dev.klevente.coupunch.usermanager.user.dto

class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val scope: Array<String>,
)