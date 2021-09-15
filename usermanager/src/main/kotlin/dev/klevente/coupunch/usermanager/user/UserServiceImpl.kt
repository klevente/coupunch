package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.library.exception.BadRequestException
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.security.AuthenticationFacade
import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.company.CompanyService
import dev.klevente.coupunch.usermanager.user.company.dto.toResponse
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
    private val authenticationFacade: AuthenticationFacade,
    private val userEventPublisher: UserEventPublisher,
    private val companyService: CompanyService
) : UserActions, UserEvents, UserService {

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
        val user = getUser(id)
        return updateUser(user, request)
    }

    @Transactional
    override fun updateCurrentUser(request: UserUpdateRequest): UserResponse {
        val user = getCurrentUser()
        return updateUser(user, request)
    }

    @Transactional
    override fun updatePassword(id: Long, request: UserPasswordUpdateRequest): UserResponse {
        val user = getUser(id)
        return updatePassword(user, request)
    }

    @Transactional
    override fun updateCurrentUserPassword(request: UserPasswordUpdateRequest): UserResponse {
        val user = getCurrentUser()
        return updatePassword(user, request)
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

    @Transactional
    override fun addCompanyToUsersList(event: UserAddEvent) {
        val user = getUser(event.userId)
        val company = companyService.getCompany(event.companyId)

        user.companies.add(company)
    }

    override fun getCurrentUserCompanies(): UserCompaniesResponse {
        val user = getCurrentUser()
        return user.companies.toResponse()
    }

    override fun resendInfoToCurrentUsersCompanies() {
        val user = getCurrentUser()
        userEventPublisher.updateUserInCompanies(user)
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

    private fun updateUser(user: User, request: UserUpdateRequest): UserResponse {
        log.info("Updating User #${user.id} (${authenticationFacade.authInfo})")

        val emailLowercase = request.email.lowercase()
        val usernameLowercase = request.username.lowercase()

        checkUserExistsAndThrow(emailLowercase, usernameLowercase, user)

        user.apply {
            email = emailLowercase
            username = usernameLowercase
            name = request.name
        }

        userEventPublisher.updateUserInCompanies(user)

        return user.toResponse()
    }

    private fun updatePassword(user: User, request: UserPasswordUpdateRequest): UserResponse {
        val passwordHashed = passwordEncoder.encode(request.password)
        user.apply {
            password = passwordHashed
        }

        return user.toResponse()
    }
}