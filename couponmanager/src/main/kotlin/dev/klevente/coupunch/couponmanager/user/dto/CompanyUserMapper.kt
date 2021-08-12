package dev.klevente.coupunch.couponmanager.user.dto

import dev.klevente.coupunch.couponmanager.security.authorization.Role
import dev.klevente.coupunch.couponmanager.user.CompanyUser
import dev.klevente.coupunch.library.util.mapToArray

fun CompanyUser.toResponse(companyName: String, companyUrl: String, companyCurrency: String) = CompanyUserResponse(
    id = id,
    username = username,
    scope = roles.mapToArray(Role::name),
    companyName = companyName,
    companyUrl = companyUrl,
    companyCurrency = companyCurrency
)