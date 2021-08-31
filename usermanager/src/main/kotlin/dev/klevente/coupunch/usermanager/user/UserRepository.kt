package dev.klevente.coupunch.usermanager.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    @Deprecated("This method needs to be used inside the UserRepository class to make Spring Data work.", ReplaceWith
        ("findFirstByUsernameOrEmail(userOrEmail)")
    )
    fun findFirstByUsernameOrEmail(username: String, email: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByUsername(username: String): Boolean

    fun findByUsernameContaining(username: String): List<User>

    fun findFirstByCode(code: String): User?
}

fun UserRepository.findFirstByUsernameOrEmail(userOrEmail: String) = findFirstByUsernameOrEmail(userOrEmail,
    userOrEmail)