package dev.klevente.coupunch.couponmanager.product.dto

import dev.klevente.coupunch.couponmanager.product.Product

fun Product.toResponse() = ProductResponse(
    id = id,
    name = name,
    price = price
)