package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.library.exception.BadRequestException
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.security.AuthenticationFacade
import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.dto.*
import dev.klevente.coupunch.usermanager.util.uuid
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
    private val authenticationFacade: AuthenticationFacade
) : UserActions, UserService {

    override fun getUser(id: Long) =
        userRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(User::class, id)

    override fun getCurrentUser(): User {
        return getUser(authenticationFacade.userId)
    }

    @Transactional
    override fun register(request: UserAddRequest): UserResponse {
        log.info("Registering ${request.username}: ${request.email} (${authenticationFacade
            .remoteAddress})")

        val emailLowercase = request.email.lowercase()
        val usernameLowercase = request.username.lowercase()

        checkUserExistsAndThrow(emailLowercase, usernameLowercase)

        val passwordHashed = passwordEncoder.encode(request.password)

        val user = userRepository.save(
            User(
                email = emailLowercase,
                username = usernameLowercase,
                password = passwordHashed,
                code = uuid(),
                roles = hashSetOf(Role.USER)
            )
        )

        log.info("Registration successful for $usernameLowercase")

        return user.toResponse()
    }

    override fun getUserResponse(id: Long) = getUser(id).toResponse()

    override fun getCurrentUserResponse() = getCurrentUser().toResponse()

    @Transactional
    override fun updateUser(id: Long, request: UserUpdateRequest): UserResponse {
        log.info("Updating User #$id (${authenticationFacade.authInfo})")

        val user = getUser(id)

        val emailLowercase = request.email.lowercase()
        val usernameLowercase = request.username.lowercase()

        checkUserExistsAndThrow(emailLowercase, usernameLowercase, user)

        user.apply {
            email = emailLowercase
            username = usernameLowercase
        }

        return user.toResponse()
    }

    override fun updatePassword(id: Long, request: UserPasswordUpdateRequest) {
        TODO("Not yet implemented")
    }

    override fun searchUserByUsername(keyword: String): UsersForCompanyResponse {
        val formattedKeyword = keyword.trim().lowercase()
        return userRepository.findByUsernameContaining(formattedKeyword).toCompanyResponse()
    }

    override fun getUserByCode(code: String): UserForCompanyResponse {
        val user = userRepository.findFirstByCode(code)
            ?: throw EntityNotFoundException(User::class, "code", code)
        return user.toCompanyResponse()
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