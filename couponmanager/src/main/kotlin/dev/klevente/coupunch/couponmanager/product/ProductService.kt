package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductResponse
import dev.klevente.coupunch.couponmanager.product.dto.ProductUpdateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductsResponse

interface ProductService {
    fun getProduct(id: Long): Product

    fun addProduct(request: ProductCreateRequest): Product

    fun updateProduct(id: Long, request: ProductUpdateRequest)

    fun deleteProduct(id: Long)

    fun getProductResponse(id: Long): ProductResponse

    fun getProductsResponse(): ProductsResponse
}