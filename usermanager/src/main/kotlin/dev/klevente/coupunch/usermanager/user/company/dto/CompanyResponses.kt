package dev.klevente.coupunch.usermanager.user.company.dto

class CompaniesResponse(
    val companies: Array<CompanyResponse>
)

class CompanyResponse(
    val id: Long,
    val name: String,
    val url: String
)