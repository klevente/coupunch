package dev.klevente.coupunch.couponmanager.product.dto

import java.math.BigDecimal

class ProductsResponse(
    val products: Array<ProductResponse>
)

class ProductResponse(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val group: ProductGroupResponse?
)