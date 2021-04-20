package dev.klevente.coupunch.couponmanager.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

class CompanyUserAddRequest(
    @field:NotBlank
    @JsonProperty("username")
    private val _username: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {
    val username get() = _username!!
    val password get() = _password!!
}