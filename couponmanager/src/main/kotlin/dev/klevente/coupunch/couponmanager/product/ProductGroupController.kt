package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductGroupUpdateRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/product-groups")
class ProductGroupController(
    private val productGroupActions: ProductGroupActions
) {
    @GetMapping
    fun getProductGroups() = ok(productGroupActions.getProductGroupsResponse())

    @GetMapping("{productGroupId}")
    fun getProductGroup(
        @PathVariable productGroupId: Long
    ) = ok(productGroupActions.getProductGroupResponse(productGroupId))

    @PostMapping
    fun addProductGroup(
        @RequestBody @Valid request: ProductGroupCreateRequest
    ) = ok(productGroupActions.addProductGroup(request))

    @PutMapping("{productGroupId}")
    fun updateProductGroup(
        @PathVariable productGroupId: Long,
        @RequestBody @Valid request: ProductGroupUpdateRequest
    ) = ok(productGroupActions.updateProductGroup(productGroupId, request))

    @DeleteMapping("{productGroupId}")
    fun deleteProductGroup(
        @PathVariable productGroupId: Long
    ) = ok(productGroupActions.deleteProductGroup(productGroupId))
}