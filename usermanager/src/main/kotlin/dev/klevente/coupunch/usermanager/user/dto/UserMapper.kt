package dev.klevente.coupunch.usermanager.user.dto

import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.User

fun User.toResponse() = UserResponse(
    id = id,
    username = username,
    email = email,
    scope = roles.map(Role::name).toTypedArray()
)