package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.coupon.CouponService
import dev.klevente.coupunch.couponmanager.product.dto.*
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductGroupServiceImpl(
    private val log: Logger,
    private val productGroupRepository: ProductGroupRepository,
    private val couponService: CouponService
) : ProductGroupActions, ProductGroupService {
    override fun getProductGroup(id: Long) = productGroupRepository.findByIdOrNull(id)
        ?: throw EntityNotFoundException.byId(ProductGroup::class, id)

    @Transactional
    override fun addProductGroup(request: ProductGroupCreateRequest): ProductGroupResponse {
        val productGroup = productGroupRepository.save(
            ProductGroup(
                name = request.name
            )
        )

        return productGroup.toResponse()
    }

    @Transactional
    override fun updateProductGroup(id: Long, request: ProductGroupUpdateRequest): ProductGroupResponse {
        val productGroup = getProductGroup(id)

        productGroup.apply {
            name = request.name
        }

        return productGroup.toResponse()
    }

    @Transactional
    override fun deleteProductGroup(id: Long): ProductGroupResponse {
        val productGroup = getProductGroup(id)
        val ret = productGroup.toResponse()

        couponService.removeDeletedProductGroupFromCoupons(productGroup)

        productGroup.products.forEach { it.group = null }
        productGroupRepository.delete(productGroup)

        return ret
    }

    override fun getProductGroupResponse(id: Long) = getProductGroup(id).toResponse()

    override fun getProductGroupsResponse() = productGroupRepository.findAll().toResponse()
}