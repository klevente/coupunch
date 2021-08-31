package dev.klevente.coupunch.usermanager.user.dto

import dev.klevente.coupunch.library.util.mapToArray
import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.User

fun User.toResponse() = UserResponse(
    id = id,
    username = username,
    email = email,
    scope = roles.mapToArray(Role::name)
)

fun User.toCompanyResponse() = UserForCompanyResponse(
    id = id,
    username = username,
    name = name,
    code = code
)

fun Collection<User>.toCompanyResponse() = UsersForCompanyResponse(
    results = mapToArray(User::toCompanyResponse)
)