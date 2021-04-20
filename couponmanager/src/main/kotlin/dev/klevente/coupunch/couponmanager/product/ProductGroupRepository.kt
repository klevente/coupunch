package dev.klevente.coupunch.couponmanager.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductGroupRepository : JpaRepository<ProductGroup, Long> {

    fun findByName(name: String): ProductGroup?

    fun findByIdIn(ids: Collection<Long>): Set<ProductGroup>
}