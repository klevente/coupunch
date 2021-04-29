package dev.klevente.coupunch.couponmanager.product

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

    override fun getDefaultGroup() = productGroupRepository.findByName("default")!!

    override fun addProductGroup() {
        TODO("Not yet implemented")
    }

    override fun updateProductGroup() {
        TODO("Not yet implemented")
    }

    override fun deleteProductGroup(id: Long) {
        TODO("Not yet implemented")
    }
}