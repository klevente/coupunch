package dev.klevente.coupunch.couponmanager.user

import dev.klevente.coupunch.couponmanager.user.dto.CompanyUserAddRequest
import dev.klevente.coupunch.couponmanager.user.dto.CompanyUserResponse

interface CompanyUserService {
    fun getUser(id: Long): CompanyUser

    fun getCurrentUser(): CompanyUser

    fun register(request: CompanyUserAddRequest): CompanyUser

    fun getUserResponse(id: Long): CompanyUserResponse

    fun getCurrentUserResponse(): CompanyUserResponse
}