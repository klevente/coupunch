package dev.klevente.coupunch.couponmanager.config.dto

fun String.toNameResponse() = NameResponse(
    name = this
)

fun String.toUrlResponse() = UrlResponse(
    url = this
)

fun String.toSettingsResponse() = SettingsResponse(
    name = this
)