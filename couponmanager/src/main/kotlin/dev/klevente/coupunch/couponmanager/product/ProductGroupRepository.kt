package dev.klevente.coupunch.couponmanager.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductGroupRepository : JpaRepository<ProductGroup, Long> {

    fun findByIdIn(ids: Collection<Long>): List<ProductGroup>
}