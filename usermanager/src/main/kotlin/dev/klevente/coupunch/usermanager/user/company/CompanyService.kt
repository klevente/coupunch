package dev.klevente.coupunch.usermanager.user.company

interface CompanyService {
    fun getCompany(id: String): Company
}