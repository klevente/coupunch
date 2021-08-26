package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-groups")
class ProductGroupController(
    private val productGroupActions: ProductGroupActions
) {
    @GetMapping
    fun getProductGroups() = productGroupActions.getProductGroupsResponse()

    @GetMapping("{productGroupId}")
    fun getProductGroup(
        @PathVariable productGroupId: Long
    ) = productGroupActions.getProductGroupResponse(productGroupId)

    @PostMapping
    fun addProductGroup(
        @RequestBody request: ProductGroupCreateRequest
    ) = productGroupActions.addProductGroup(request)

    @PutMapping("{productGroupId}")
    fun updateProductGroup(
        @PathVariable productGroupId: Long,
        @RequestBody request: ProductGroupUpdateRequest
    ) = productGroupActions.updateProductGroup(productGroupId, request)

    @DeleteMapping("{productGroupId}")
    fun deleteProductGroup(
        @PathVariable productGroupId: Long
    ) = productGroupActions.deleteProductGroup(productGroupId)
}