package dev.klevente.coupunch.couponmanager.product.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

class ProductGroupCreateRequest(

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,
) {
    val name get() = _name!!
}

typealias ProductGroupUpdateRequest = ProductGroupCreateRequest
