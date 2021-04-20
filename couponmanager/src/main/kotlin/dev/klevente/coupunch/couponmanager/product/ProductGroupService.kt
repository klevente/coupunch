package dev.klevente.coupunch.couponmanager.product

interface ProductGroupService {
    fun getProductGroup(id: Long): ProductGroup

    fun getDefaultGroup(): ProductGroup

    fun addProductGroup()

    fun updateProductGroup()

    fun deleteProductGroup(id: Long)

    fun getGroupsByIds(ids: Collection<Long>): Set<ProductGroup>
}