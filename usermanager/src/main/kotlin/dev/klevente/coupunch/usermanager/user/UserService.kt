package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.NewUserRequest

interface UserService {
    fun register(newUserRequest: NewUserRequest): Long
}