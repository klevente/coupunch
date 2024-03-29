package dev.klevente.coupunch.couponmanager.product.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

class ProductCreateRequest(

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:Positive
    @JsonProperty("price")
    private val _price: BigDecimal?,

    @JsonProperty("group")
    private val _group: Long?,
) {
    val name get() = _name!!
    val price get() = _price!!
    val group get() = _group
}

typealias ProductUpdateRequest = ProductCreateRequest
