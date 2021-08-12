package dev.klevente.coupunch.couponmanager.product.dto

import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.library.util.mapToArray

fun Collection<Product>.toResponse() = ProductsResponse(
    products = mapToArray { it.toResponse() }
)

fun Product.toResponse() = ProductResponse(
    id = id,
    name = name,
    price = price,
    group = group?.toResponse()
)