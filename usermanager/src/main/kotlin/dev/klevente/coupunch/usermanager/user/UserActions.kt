package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.*

interface UserActions {
    fun register(request: UserAddRequest): UserResponse

    fun getUserResponse(id: Long): UserResponse

    fun getCurrentUserResponse(): UserResponse

    fun updateUser(id: Long, request: UserUpdateRequest): UserResponse

    fun updateCurrentUser(request: UserUpdateRequest): UserResponse

    fun updatePassword(id: Long, request: UserPasswordUpdateRequest): UserResponse

    fun updateCurrentUserPassword(request: UserPasswordUpdateRequest): UserResponse

    fun searchUserByUsername(keyword: String): UsersForCompanyResponse

    fun getUserByCode(code: String): UserForCompanyResponse
}