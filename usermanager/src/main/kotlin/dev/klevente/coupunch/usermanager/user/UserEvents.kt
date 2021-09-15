package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.UserAddEvent

interface UserEvents {
    fun addCompanyToUsersList(event: UserAddEvent)
}