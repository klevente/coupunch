package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.UserAddRequest
import dev.klevente.coupunch.usermanager.user.dto.UserPasswordUpdateRequest
import dev.klevente.coupunch.usermanager.user.dto.UserResponse
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest

interface UserService {
    fun getUser(id: Long): User

    fun getCurrentUser(): User

    fun register(request: UserAddRequest): User

    fun getUserResponse(id: Long): UserResponse

    fun getCurrentUserResponse(): UserResponse

    fun updateUser(id: Long, request: UserUpdateRequest): User

    fun updatePassword(id: Long, request: UserPasswordUpdateRequest)
}