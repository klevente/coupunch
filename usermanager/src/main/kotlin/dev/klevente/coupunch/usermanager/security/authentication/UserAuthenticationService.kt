package dev.klevente.coupunch.usermanager.security.authentication

import dev.klevente.coupunch.usermanager.user.Role
import dev.klevente.coupunch.usermanager.user.UserRepository
import org.slf4j.Logger
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(
    private val logger: Logger,
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val usernameLowercase = username.toLowerCase()
        val user = userRepository.findFirstByUsername(usernameLowercase) ?: throw IllegalArgumentException("User not found!")
        logger.info("Login request for $usernameLowercase")

        return AuthUser(
            id = user.id,
            username = user.email,
            password = user.password,
            authorities = user.roles.map(Role::grantedAuthority).toSet()
        )
    }
}