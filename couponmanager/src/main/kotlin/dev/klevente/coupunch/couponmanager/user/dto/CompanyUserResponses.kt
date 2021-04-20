package dev.klevente.coupunch.couponmanager.user.dto

class CompanyUserResponse(
    val id: Long,
    val username: String,
    val companyUrl: String,
    val companyName: String,
    val scope: Array<String>,
)