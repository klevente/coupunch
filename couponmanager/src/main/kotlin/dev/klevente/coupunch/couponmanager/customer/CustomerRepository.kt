package dev.klevente.coupunch.couponmanager.customer

import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByUsername(username: String): Customer?

    fun findByCode(code: String): Customer?
}