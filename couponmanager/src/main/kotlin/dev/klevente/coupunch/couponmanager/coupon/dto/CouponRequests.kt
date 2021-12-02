package dev.klevente.coupunch.couponmanager.coupon.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class CouponCreateRequest(
    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @JsonProperty("type")
    private val _type: String?,

    @field:Valid
    @field:NotNull
    @JsonProperty("eligibleItems")
    private val _eligibleItems: EligibleItemsRequest?,

    @field:Valid
    @field:NotEmpty
    @JsonProperty("rewards")
    private val _rewards: Array<RewardRequest>?
) {
    val name get() = _name!!
    val type get() = _type!!
    val eligibleItems get() = _eligibleItems!!
    val rewards get() = _rewards!!
}

typealias CouponUpdateRequest = CouponCreateRequest

class EligibleItemsRequest(

    @field:Valid
    @field:NotEmpty
    @JsonProperty("products")
    private val _products: Array<EligibleProductRequest>?,

    @field:Valid
    @field:NotEmpty
    @JsonProperty("productGroups")
    private val _productGroups: Array<EligibleProductGroupRequest>?
) {
    val products get() = _products!!
    val productGroups get() = _productGroups!!
}

class EligibleProductRequest(

    @field:Positive
    @JsonProperty("id")
    private val _id: Long?,

    val points: Int?
) {
    val id get() = _id!!
}

class EligibleProductGroupRequest(

    @field:Positive
    @JsonProperty("id")
    private val _id: Long?,

    val points: Int?
) {
    val id get() = _id!!
}

class RewardRequest(

    @field:Positive
    @JsonProperty("threshold")
    private val _threshold: BigDecimal?,

    @field:NotBlank
    @JsonProperty("discountType")
    private val _discountType: String?,

    @field:Positive
    @JsonProperty("discount")
    private val _discount: BigDecimal?,

    @field:Valid
    @field:NotEmpty
    @JsonProperty("products")
    private val _products: Array<ProductRewardRequest>?,

    @field:Valid
    @field:NotEmpty
    @JsonProperty("productGroups")
    private val _productGroups: Array<ProductGroupRewardRequest>?
) {
    val threshold get() = _threshold!!
    val discountType get() = _discountType!!
    val discount get() = _discount!!
    val products get() = _products!!
    val productGroups get() = _productGroups!!
}

class ProductRewardRequest(

    @field:Positive
    @JsonProperty("id")
    private val _id: Long?,

    @field:Positive
    @JsonProperty("amount")
    private val _amount: Int?
) {
    val id get() = _id!!
    val amount get() = _amount!!
}

class ProductGroupRewardRequest(

    @field:Positive
    @JsonProperty("id")
    private val _id: Long?,

    @field:Positive
    @JsonProperty("amount")
    private val _amount: Int?
) {
    val id get() = _id!!
    val amount get() = _amount!!
}
