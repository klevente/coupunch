package dev.klevente.coupunch.couponmanager.product

import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductGroupServiceImpl(
    private val log: Logger,
    private val productGroupRepository: ProductGroupRepository
) : ProductGroupService {
    override fun getProductGroup(id: Long): ProductGroup {
        TODO("Not yet implemented")
    }

    override fun addProductGroup() {
        TODO("Not yet implemented")
    }

    override fun updateProductGroup() {
        TODO("Not yet implemented")
    }

    override fun deleteProductGroup(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getProductsByIds(ids: Collection<Long>) = productGroupRepository.findByIdIn(ids)
}