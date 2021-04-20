package dev.klevente.coupunch.couponmanager.user

import org.springframework.data.jpa.repository.JpaRepository

interface CompanyUserRepository : JpaRepository<CompanyUser, Long> {
    fun findFirstByUsername(username: String): CompanyUser?
}