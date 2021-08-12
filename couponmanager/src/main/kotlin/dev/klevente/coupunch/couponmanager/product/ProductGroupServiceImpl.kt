package dev.klevente.coupunch.couponmanager.product

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
    private val productGroupRepository: ProductGroupRepository
) : ProductGroupService {
    override fun getProductGroup(id: Long) = productGroupRepository.findByIdOrNull(id)
        ?: throw EntityNotFoundException.byId(ProductGroup::class, id)

    @Transactional
    override fun addProductGroup(request: ProductGroupCreateRequest): ProductGroup {
        val group = productGroupRepository.save(
            ProductGroup(
                name = request.name
            )
        )

        return group
    }

    @Transactional
    override fun updateProductGroup(id: Long, request: ProductGroupUpdateRequest) {
        val group = getProductGroup(id)

        group.apply {
            name = request.name
        }
    }

    @Transactional
    override fun deleteProductGroup(id: Long) {
        val group = getProductGroup(id)
        group.products.forEach { it.group = null }
        productGroupRepository.delete(group)
    }

    override fun getProductGroupResponse(id: Long) = getProductGroup(id).toResponse()

    override fun getProductGroupsResponse() = productGroupRepository.findAll().toResponse()
}