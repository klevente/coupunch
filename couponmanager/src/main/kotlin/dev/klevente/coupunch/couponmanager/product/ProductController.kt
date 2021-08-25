package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun getProducts() = productService.getProductsResponse()

    @GetMapping("{productId}")
    fun getProduct(
        @PathVariable productId: Long
    ) = productService.getProductResponse(productId)

    @PostMapping
    fun addProduct(
        @RequestBody request: ProductCreateRequest
    ) = productService.addProduct(request)

    @PutMapping("{productId}")
    fun updateProduct(
        @PathVariable productId: Long,
        @RequestBody request: ProductUpdateRequest
    ) = productService.updateProduct(productId, request)

    @DeleteMapping("{productId}")
    fun deleteProduct(
        @PathVariable productId: Long
    ) = productService.deleteProduct(productId)
}