package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.couponmanager.config.dto.NameResponse
import dev.klevente.coupunch.couponmanager.config.dto.SettingsResponse
import dev.klevente.coupunch.couponmanager.config.dto.SettingsUpdateRequest
import dev.klevente.coupunch.couponmanager.config.dto.UrlResponse

interface CompanyConfigActions {
    fun getCompanyNameResponse(): NameResponse

    fun getMetabaseUrlResponse(): UrlResponse

    fun generateMetabaseIframeUrlResponse(): UrlResponse

    fun getSettings(): SettingsResponse

    fun updateSettings(request: SettingsUpdateRequest): SettingsResponse

    fun resendUpdateSettings()
}