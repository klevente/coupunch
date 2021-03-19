package dev.klevente.coupunch.couponmanager.product

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController {

    @GetMapping
    fun getProducts() {

    }

    @PostMapping
    fun addProduct() {

    }

    @GetMapping("{productId}")
    fun getProduct(@PathVariable productId: Long) {

    }

    @PutMapping("{productId}")
    fun updateProduct(@PathVariable productId: Long) {

    }

    @DeleteMapping("{productId}")
    fun deleteProduct(@PathVariable productId: Long) {

    }
}