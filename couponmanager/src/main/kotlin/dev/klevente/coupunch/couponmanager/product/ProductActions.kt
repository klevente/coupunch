package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductResponse
import dev.klevente.coupunch.couponmanager.product.dto.ProductUpdateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductsResponse

interface ProductActions {
    fun addProduct(request: ProductCreateRequest): ProductResponse

    fun updateProduct(id: Long, request: ProductUpdateRequest): ProductResponse

    fun deleteProduct(id: Long): ProductResponse

    fun getProductResponse(id: Long): ProductResponse

    fun getProductsResponse(): ProductsResponse
}