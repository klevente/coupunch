package dev.klevente.coupunch.couponmanager.checkout.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class CheckoutRequest(

    @field:Valid
    @field:NotNull
    @JsonProperty("products")
    val _products: Array<PurchasedProduct>?,

    @field:Valid
    @field:NotNull
    @JsonProperty("coupons")
    val _coupons: Array<RedeemedCoupon>?
) {
    val products get() = _products!!
    val coupons get() = _coupons!!
}

class PurchasedProduct(

    @field:Positive
    @JsonProperty("id")
    val _id: Long?,

    @field:Positive
    @JsonProperty("amount")
    val _amount: Int?,
) {
    val id get() = _id!!
    val amount get() = _amount!!
}

class RedeemedCoupon(
    @field:Positive
    @JsonProperty("id")
    val _id: Long?,

    @field:Positive
    @JsonProperty("productId")
    val _productId: Long?,

    @field:Positive
    @JsonProperty("amount")
    val _amount: Int?,
) {
    val id get() = _id!!
    val productId get() = _productId!!
    val amount get() = _amount!!
}