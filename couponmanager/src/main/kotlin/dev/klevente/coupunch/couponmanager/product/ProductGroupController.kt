package dev.klevente.coupunch.couponmanager.product

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-groups")
class ProductGroupController {

    @GetMapping
    fun getProductGroups() {

    }

    @PostMapping
    fun addProductGroup() {

    }

    @GetMapping("{groupId}")
    fun getProductGroup(@PathVariable groupId: Long) {

    }

    @PutMapping("{groupId}")
    fun updateProductGroup(@PathVariable groupId: Long) {

    }

    @DeleteMapping("{groupId}")
    fun deleteProductGroup(@PathVariable groupId: Long) {

    }
}