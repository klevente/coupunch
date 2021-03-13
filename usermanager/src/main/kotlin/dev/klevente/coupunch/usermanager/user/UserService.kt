package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.NewUserRequest
import dev.klevente.coupunch.usermanager.user.dto.UserResponse
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest

interface UserService {
    fun getUser(id: Long): User

    fun register(request: NewUserRequest): Long

    fun getUserResponse(id: Long): UserResponse

    fun updateUser(id: Long, request: UserUpdateRequest)
}