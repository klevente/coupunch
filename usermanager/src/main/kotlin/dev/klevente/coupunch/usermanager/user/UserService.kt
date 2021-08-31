package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.*

interface UserService {
    fun getUser(id: Long): User

    fun getCurrentUser(): User

}