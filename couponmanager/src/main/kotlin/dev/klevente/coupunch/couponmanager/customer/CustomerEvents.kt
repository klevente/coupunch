package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.UserUpdateEvent

interface CustomerEvents {
    fun update(event: UserUpdateEvent)
}