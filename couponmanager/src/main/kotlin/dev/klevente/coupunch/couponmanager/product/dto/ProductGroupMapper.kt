package dev.klevente.coupunch.couponmanager.product.dto

import dev.klevente.coupunch.couponmanager.product.ProductGroup
import dev.klevente.coupunch.library.util.mapToArray

fun Collection<ProductGroup>.toResponse() = ProductGroupsResponse(
    productGroups = mapToArray { it.toResponse() }
)

fun ProductGroup.toResponse() = ProductGroupResponse(
    id = id,
    name = name,
)