package dev.klevente.coupunch.usermanager.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/*
class NewUserRequest(
    @field:NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,

    @field:NotBlank
    @JsonProperty("username")
    private val _username: String?,

    @field:NotBlank
    @field:Size(min = 8, max = 32)
    @JsonProperty("password")
    private val _password: String?,
) {
    val email     get() = _email!!
    val username  get() = _username!!
    val password  get() = _password!!
}*/

class NewUserRequest(
    @field:NotBlank
    @field:Email
    val email: String = "",

    @field:NotBlank
    val username: String = "",

    @field:NotBlank
    val password: String = "",
)
