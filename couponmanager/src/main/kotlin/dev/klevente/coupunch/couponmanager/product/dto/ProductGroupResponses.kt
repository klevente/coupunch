package dev.klevente.coupunch.couponmanager.product.dto

class ProductGroupsResponse(
    val productGroups: Array<ProductGroupResponse>
)

class ProductGroupResponse(
    val id: Long,
    val name: String,
)