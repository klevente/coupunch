package dev.klevente.coupunch.couponmanager.product.dto

class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val group: ProductGroupSimpleResponse
)