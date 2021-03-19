package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.library.exception.BadRequestException
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.security.AuthenticationFacade
import dev.klevente.coupunch.usermanager.security.authorization.RoleService
import dev.klevente.coupunch.usermanager.user.dto.UserAddRequest
import dev.klevente.coupunch.usermanager.user.dto.UserPasswordUpdateRequest
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
        userRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(User::class, id)

    @Transactional
    override fun register(request: UserAddRequest): User {
        log.info("Registering ${request.username}: ${request.email} (${authenticationFacade
            .remoteAddress})")

        val emailLowercase = request.email.toLowerCase()
        val usernameLowercase = request.username.toLowerCase()

        checkUserExistsAndThrow(emailLowercase, usernameLowercase)

        val passwordHashed = passwordEncoder.encode(request.password)

        val user = userRepository.save(
            User(
                email = emailLowercase,
                username = usernameLowercase,
                password = passwordHashed,
                roles = hashSetOf(roleService.USER)
            )
        )

        log.info("Registration successful for $usernameLowercase")

        return user
    }

    override fun getUserResponse(id: Long) = getUser(id).toResponse()

    @Transactional
    override fun updateUser(id: Long, request: UserUpdateRequest): User {
        log.info("Updating User #$id (${authenticationFacade.authInfo})")

        val user = getUser(id)

        val emailLowercase = request.email.toLowerCase()
        val usernameLowercase = request.username.toLowerCase()

        checkUserExistsAndThrow(emailLowercase, usernameLowercase, user)

        user.apply {
            email = emailLowercase
            username = usernameLowercase
        }

        return user
    }

    override fun updatePassword(id: Long, request: UserPasswordUpdateRequest) {
        TODO("Not yet implemented")
    }

    private fun checkUserExistsAndThrow(email: String, username: String, user: User? = null) {
        when {
            user?.email != email && userRepository.existsByEmail(email) -> {
                throw BadRequestException("This email is already taken!")
            }
            user?.username != username && userRepository.existsByUsername(username) -> {
                throw BadRequestException("This username is already taken!")
            }
        }
    }
}