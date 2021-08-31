package dev.klevente.coupunch.couponmanager.customer.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

class CustomerCreateRequest(

    @Positive
    @JsonProperty("id")
    private val _id: Long?,

    @NotBlank
    @JsonProperty("username")
    private val _username: String?,

    @NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @NotBlank
    @JsonProperty("code")
    private val _code: String?
) {
    val id get() = _id!!
    val username get() = _username!!
    val name get() = _name!!
    val code get() = _code!!
}

class CustomerUpdateRequest(
    @NotBlank
    @JsonProperty("username")
    private val _username: String?,

    @NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @NotBlank
    @JsonProperty("code")
    private val _code: String?
) {
    val username get() = _username!!
    val name get() = _name!!
    val code get() = _code!!
}