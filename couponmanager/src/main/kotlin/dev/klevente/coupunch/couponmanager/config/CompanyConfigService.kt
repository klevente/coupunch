package dev.klevente.coupunch.couponmanager.config

interface CompanyConfigService {
    fun getCompanyId(): String

    fun getCompanyName(): String

    fun getCompanyUrl(): String

    fun getCompanyCurrency(): String
}