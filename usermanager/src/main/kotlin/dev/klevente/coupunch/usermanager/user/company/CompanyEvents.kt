package dev.klevente.coupunch.usermanager.user.company

import dev.klevente.coupunch.usermanager.user.company.dto.CompanyUpdateEvent

interface CompanyEvents {
    fun updateOrAddCompany(event: CompanyUpdateEvent)
}