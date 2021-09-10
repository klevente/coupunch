package dev.klevente.coupunch.usermanager.user.dto

class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val scope: Array<String>,
)

class UsersForCompanyResponse(
    val results: Array<UserForCompanyResponse>
)

class UserForCompanyResponse(
    val id: Long,
    val username: String,
    val name: String,
    val code: String
)

class UserCompaniesResponse(
    val companies: Array<UserCompanyResponse>
)

class UserCompanyResponse(
    val id: Long,
    val name: String,
    val url: String
)