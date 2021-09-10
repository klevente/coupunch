package dev.klevente.coupunch.usermanager.user.company.dto

import dev.klevente.coupunch.library.util.mapToArray
import dev.klevente.coupunch.usermanager.user.company.Company
import dev.klevente.coupunch.usermanager.user.dto.UserCompaniesResponse
import dev.klevente.coupunch.usermanager.user.dto.UserCompanyResponse

fun Collection<Company>.toResponse() = UserCompaniesResponse(
    companies = mapToArray(Company::toResponse)
)

fun Company.toResponse() = UserCompanyResponse(
    id = id,
    name = name,
    url = url
)