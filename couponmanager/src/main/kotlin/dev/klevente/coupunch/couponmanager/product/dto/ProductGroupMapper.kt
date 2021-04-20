package dev.klevente.coupunch.couponmanager.product.dto

import dev.klevente.coupunch.couponmanager.product.ProductGroup

fun ProductGroup.toResponse() = ProductGroupResponse()

fun ProductGroup.toSimpleResponse() = ProductGroupSimpleResponse(
    id = id,
    name = name,
)