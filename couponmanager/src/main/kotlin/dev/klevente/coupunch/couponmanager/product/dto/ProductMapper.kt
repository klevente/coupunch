package dev.klevente.coupunch.couponmanager.product.dto

import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup

fun Product.toResponse() = ProductResponse(
    id = id,
    name = name,
    price = price,
    group = group.toSimpleResponse()
)