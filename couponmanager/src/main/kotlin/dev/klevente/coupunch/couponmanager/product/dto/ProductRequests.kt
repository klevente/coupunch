package dev.klevente.coupunch.couponmanager.product.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

class ProductCreateRequest(

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:Positive
    @JsonProperty("price")
    private val _price: Double?,

    @JsonProperty("groups")
    private val _groups: LongArray?,
) {
    val name get() = _name!!
    val price get() = _price!!
    val groups get() = _groups!!
}

class ProductUpdateRequest(

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:Positive
    @JsonProperty("price")
    private val _price: Double?,

    @JsonProperty("groups")
    private val _groups: LongArray?,
) {
    val name get() = _name!!
    val price get() = _price!!
    val groups get() = _groups!!
}

