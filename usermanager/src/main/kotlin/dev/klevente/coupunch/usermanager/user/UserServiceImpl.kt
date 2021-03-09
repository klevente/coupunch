package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.library.security.AuthenticationFacade
import dev.klevente.coupunch.usermanager.security.authorization.RoleService
import dev.klevente.coupunch.usermanager.user.dto.NewUserRequest
import org.slf4j.Logger
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val logger: Logger,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleService: RoleService,
    private val authenticationFacade: AuthenticationFacade
) : UserService {

    @Transactional
    override fun register(newUserRequest: NewUserRequest): Long {
        logger.info("Registering ${newUserRequest.username}: ${newUserRequest.email} (${authenticationFacade
            .remoteAddress})")

        val emailLowercase = newUserRequest.email
        val usernameLowercase = newUserRequest.username
        val passwordHashed = passwordEncoder.encode(newUserRequest.password)

        checkUserExistsAndThrow(emailLowercase, usernameLowercase)

        val user = userRepository.save(
            User(
                email = emailLowercase,
                username = usernameLowercase,
                password = passwordHashed,
                roles = hashSetOf(roleService.USER)
            )
        )

        logger.info("Registration successful for $usernameLowercase")

        return user.id
    }

    private fun checkUserExistsAndThrow(email: String, username: String) {
        when {
            userRepository.existsByEmail(email) -> {
                throw IllegalArgumentException("This email is already taken!")
            }
            userRepository.existsByUsername(username) -> {
                throw IllegalArgumentException("This username is already taken!")
            }
        }
    }
}