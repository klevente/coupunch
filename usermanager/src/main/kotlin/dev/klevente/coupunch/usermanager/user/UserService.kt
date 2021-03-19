package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.UserCreateRequest
import dev.klevente.coupunch.usermanager.user.dto.UserResponse
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest

interface UserService {
    fun getUser(id: Long): User

    fun register(request: UserCreateRequest): User

    fun getUserResponse(id: Long): UserResponse

    fun updateUser(id: Long, request: UserUpdateRequest)
}