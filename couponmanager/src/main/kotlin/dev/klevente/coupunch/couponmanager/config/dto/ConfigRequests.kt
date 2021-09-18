package dev.klevente.coupunch.couponmanager.config.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

class SettingsUpdateRequest(
    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?
) {
    val name get() = _name!!
}