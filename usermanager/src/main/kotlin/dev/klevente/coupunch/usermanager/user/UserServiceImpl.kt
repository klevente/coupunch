package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.library.exception.BadRequestException
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.security.AuthenticationFacade
import dev.klevente.coupunch.usermanager.security.authorization.RoleService
import dev.klevente.coupunch.usermanager.user.dto.NewUserRequest
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest
import dev.klevente.coupunch.usermanager.user.dto.toResponse
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val log: Logger,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleService: RoleService,
    private val authenticationFacade: AuthenticationFacade
) : UserService {

    override fun getUser(id: Long) =
        userRepository.findByIdOrNull(id) ?: throw EntityNotFoundException(User::class, id)

    @Transactional
    override fun register(request: NewUserRequest): Long {
        log.info("Registering ${request.username}: ${request.email} (${authenticationFacade
            .remoteAddress})")

        val emailLowercase = request.email.toLowerCase()
        val usernameLowercase = request.username.toLowerCase()
        val passwordHashed = passwordEncoder.encode(request.password)

        checkUserExistsAndThrow(emailLowercase, usernameLowercase)

        val user = userRepository.save(
            User(
                email = emailLowercase,
                username = usernameLowercase,
                password = passwordHashed,
                roles = hashSetOf(roleService.USER)
            )
        )

        log.info("Registration successful for $usernameLowercase")

        return user.id
    }

    override fun getUserResponse(id: Long) = getUser(id).toResponse()

    @Transactional
    override fun updateUser(id: Long, request: UserUpdateRequest) {
        log.info("Updating User #$id (${authenticationFacade.authInfo})")

        val user = getUser(id)

        val emailLowercase = request.email.toLowerCase()
        val usernameLowercase = request.username.toLowerCase()
        val passwordHashed = passwordEncoder.encode(request.password)

        checkUserExistsAndThrow(emailLowercase, usernameLowercase)

        user.apply {
            email = emailLowercase
            username = usernameLowercase
            password = passwordHashed
        }
    }

    private fun checkUserExistsAndThrow(email: String, username: String) {
        when {
            userRepository.existsByEmail(email) -> {
                throw BadRequestException("This email is already taken!")
            }
            userRepository.existsByUsername(username) -> {
                throw BadRequestException("This username is already taken!")
            }
        }
    }
}