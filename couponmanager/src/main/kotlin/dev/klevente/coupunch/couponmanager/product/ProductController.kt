package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductUpdateRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productActions: ProductActions
) {
    @GetMapping
    fun getProducts() = ok(productActions.getProductsResponse())

    @GetMapping("{productId}")
    fun getProduct(
        @PathVariable productId: Long
    ) = ok(productActions.getProductResponse(productId))

    @PostMapping
    fun addProduct(
        @RequestBody request: ProductCreateRequest
    ) = ok(productActions.addProduct(request))

    @PutMapping("{productId}")
    fun updateProduct(
        @PathVariable productId: Long,
        @RequestBody request: ProductUpdateRequest
    ) = ok(productActions.updateProduct(productId, request))

    @DeleteMapping("{productId}")
    fun deleteProduct(
        @PathVariable productId: Long
    ) = ok(productActions.deleteProduct(productId))
}