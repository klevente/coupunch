package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductResponse
import dev.klevente.coupunch.couponmanager.product.dto.ProductUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productActions: ProductActions
) {
    @GetMapping
    fun getProducts() = productActions.getProductsResponse()

    @GetMapping("{productId}")
    fun getProduct(
        @PathVariable productId: Long
    ) = productActions.getProductResponse(productId)

    @PostMapping
    fun addProduct(
        @RequestBody request: ProductCreateRequest
    ) = productActions.addProduct(request)

    @PutMapping("{productId}")
    fun updateProduct(
        @PathVariable productId: Long,
        @RequestBody request: ProductUpdateRequest
    ) = productActions.updateProduct(productId, request)

    @DeleteMapping("{productId}")
    fun deleteProduct(
        @PathVariable productId: Long
    ) = productActions.deleteProduct(productId)
}