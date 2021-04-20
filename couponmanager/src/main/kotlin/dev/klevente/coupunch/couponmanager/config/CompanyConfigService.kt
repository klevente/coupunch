package dev.klevente.coupunch.couponmanager.config

interface CompanyConfigService {
    fun getCompanyName(): String

    fun getCompanyUrl(): String
}