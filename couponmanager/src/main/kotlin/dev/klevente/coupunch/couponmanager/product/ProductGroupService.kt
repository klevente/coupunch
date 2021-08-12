package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupResponse
import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupUpdateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupsResponse

interface ProductGroupService {
    fun getProductGroup(id: Long): ProductGroup

    fun addProductGroup(request: ProductGroupCreateRequest): ProductGroup

    fun updateProductGroup(id: Long, request: ProductGroupUpdateRequest)

    fun deleteProductGroup(id: Long)

    fun getProductGroupResponse(id: Long): ProductGroupResponse

    fun getProductGroupsResponse(): ProductGroupsResponse
}