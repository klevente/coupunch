package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.couponmanager.config.dto.UrlResponse

interface CompanyConfigActions {
    fun getMetabaseUrl(): UrlResponse

    fun generateMetabaseIframeUrl(): UrlResponse
}