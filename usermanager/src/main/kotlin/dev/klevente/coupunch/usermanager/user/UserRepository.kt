package dev.klevente.coupunch.usermanager.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findFirstByUsername(username: String): User?
}