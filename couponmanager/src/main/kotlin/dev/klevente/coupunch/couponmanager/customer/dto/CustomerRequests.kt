package dev.klevente.coupunch.couponmanager.customer.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

class CustomerAddRequest(

    @Positive
    @JsonProperty("id")
    val _id: Long?,

    @NotBlank
    @JsonProperty("username")
    val _username: String?,

    @NotBlank
    @JsonProperty("name")
    val _name: String?,

    @NotBlank
    @JsonProperty("code")
    val _code: String?
) {
    val id get() = _id!!
    val username get() = _username!!
    val name get() = _name!!
    val code get() = _code!!
}

class CustomerUpdateRequest(
    @NotBlank
    @JsonProperty("username")
    val _username: String?,

    @NotBlank
    @JsonProperty("name")
    val _name: String?,

    @NotBlank
    @JsonProperty("code")
    val _code: String?
) {
    val username get() = _username!!
    val name get() = _name!!
    val code get() = _code!!
}