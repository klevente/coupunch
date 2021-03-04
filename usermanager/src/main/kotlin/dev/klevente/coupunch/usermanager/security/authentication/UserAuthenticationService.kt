package dev.klevente.coupunch.usermanager.security.authentication

import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.UserRepository
import org.slf4j.Logger
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAuthenticationService(
    private val logger: Logger,
    private val userRepository: UserRepository,
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        val usernameOrEmailLowercase = usernameOrEmail.toLowerCase()
        // TODO fix this
        val user = userRepository.findFirstByUsernameOrEmail(usernameOrEmailLowercase, usernameOrEmailLowercase) ?: throw
        IllegalArgumentException("User not found!")
        logger.info("Login request for $usernameOrEmailLowercase")

        return AuthUser(
            id = user.id,
            username = user.email,
            password = user.password,
            authorities = user.roles.map(Role::grantedAuthority).toSet()
        )
    }
}