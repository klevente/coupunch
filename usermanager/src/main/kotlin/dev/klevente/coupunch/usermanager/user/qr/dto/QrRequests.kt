package dev.klevente.coupunch.usermanager.user.qr.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class QrRedeemRequest(
    @field:Valid
    @field:NotNull
    @JsonProperty("redeemedCoupons")
    private val _redeemedCoupons: Array<RedeemedCouponRequest>?
) {
    val redeemedCoupons get() = _redeemedCoupons!!
}

class RedeemedCouponRequest(
    @field:Positive
    @JsonProperty("id")
    private val _id: Long?,

    @field:Positive
    @JsonProperty("productId")
    private val _productId: Long?
) {
    val id get() = _id!!
    val productId get() = _productId!!
}