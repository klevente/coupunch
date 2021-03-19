package dev.klevente.coupunch.couponmanager.product

interface ProductGroupService {
    fun getProductGroup(id: Long): ProductGroup

    fun addProductGroup()

    fun updateProductGroup()

    fun deleteProductGroup(id: Long)

    fun getProductsByIds(ids: Collection<Long>): List<ProductGroup>
}