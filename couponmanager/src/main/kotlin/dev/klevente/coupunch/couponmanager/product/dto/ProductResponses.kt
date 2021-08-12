package dev.klevente.coupunch.couponmanager.product.dto

class ProductsResponse(
    products: Array<ProductResponse>
)

class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val group: ProductGroupResponse?
)