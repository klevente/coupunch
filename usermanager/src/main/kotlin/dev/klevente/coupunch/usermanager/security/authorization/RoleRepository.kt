package dev.klevente.coupunch.usermanager.security.authorization

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun findFirstByName(name: String): Role?

    fun existsByName(name: String): Boolean
}