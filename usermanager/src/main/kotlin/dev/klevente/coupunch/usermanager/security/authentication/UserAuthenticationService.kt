package dev.klevente.coupunch.usermanager.security.authentication

import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.security.AuthUser
import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.User
import dev.klevente.coupunch.usermanager.user.UserRepository
import dev.klevente.coupunch.usermanager.user.findFirstByUsernameOrEmail
import org.slf4j.Logger
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAuthenticationService(
    private val log: Logger,
    private val userRepository: UserRepository,
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        val usernameOrEmailLowercase = usernameOrEmail.lowercase()
        val user = userRepository.findFirstByUsernameOrEmail(usernameOrEmailLowercase)
            ?: throw UsernameNotFoundException("User $usernameOrEmail not found!")
        log.info("Login request for $usernameOrEmailLowercase")

        return AuthUser.fromUser(user)
    }

    private fun AuthUser.Companion.fromUser(user: User) = AuthUser(
        id = user.id,
        username = user.username,
        password = user.password,
        authorities = user.roles.map(Role::grantedAuthority).toSet()
    )
}