package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product-groups")
class ProductGroupController(
    private val productGroupService: ProductGroupService
) {
    @GetMapping
    fun getProductGroups() = productGroupService.getProductGroupsResponse()

    @GetMapping("{productGroupId}")
    fun getProductGroup(
        @PathVariable productGroupId: Long
    ) = productGroupService.getProductGroupResponse(productGroupId)

    @PostMapping
    fun addProductGroup(
        @RequestBody request: ProductGroupCreateRequest
    ) = productGroupService.addProductGroup(request)

    @PutMapping("{productGroupId}")
    fun updateProductGroup(
        @PathVariable productGroupId: Long,
        @RequestBody request: ProductGroupUpdateRequest
    ) = productGroupService.updateProductGroup(productGroupId, request)

    @DeleteMapping("{productGroupId}")
    fun deleteProductGroup(
        @PathVariable productGroupId: Long
    ) = productGroupService.deleteProductGroup(productGroupId)
}