package dev.klevente.coupunch.couponmanager.user.dto

import dev.klevente.coupunch.couponmanager.security.authorization.Role
import dev.klevente.coupunch.couponmanager.user.CompanyUser

fun CompanyUser.toResponse(companyName: String, companyUrl: String, companyCurrency: String) = CompanyUserResponse(
    id = id,
    username = username,
    scope = roles.map(Role::name).toTypedArray(),
    companyName = companyName,
    companyUrl = companyUrl,
    companyCurrency = companyCurrency
)