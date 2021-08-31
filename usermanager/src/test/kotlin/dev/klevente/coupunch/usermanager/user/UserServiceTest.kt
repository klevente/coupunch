package dev.klevente.coupunch.usermanager.user

import com.nhaarman.mockitokotlin2.*
import dev.klevente.coupunch.library.exception.BadRequestException
import dev.klevente.coupunch.library.security.AuthenticationFacade
import dev.klevente.coupunch.testlibrary.*
import dev.klevente.coupunch.testlibrary.any
import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.dto.UserAddRequest
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest
import io.github.serpro69.kfaker.Faker
import org.assertj.core.api.BDDAssertions.catchThrowable
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    private lateinit var userService: UserActions

    @Mock
    private lateinit var log: Logger

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var authenticationFacade: AuthenticationFacade

    private val faker = Faker()

    @BeforeEach
    fun setup() {
        userService = UserServiceImpl(log, userRepository, passwordEncoder, authenticationFacade)
    }

    @Test
    fun `when user is registered, it is saved to the database`() {
        // given
        val email = faker.email()
        val username = faker.username()
        val password = faker.password()
        val hashedPassword = faker.randomChars()

        given { userRepository.save(any()) } willAnswer { it.getArgument<User>(0).withId(1) }
        given { userRepository.existsByEmail(any()) } willReturn { false }
        given { userRepository.existsByUsername(any()) } willReturn { false }
        given { passwordEncoder.encode(any()) } willReturn { hashedPassword }

        // when
        val request = UserAddRequest(email, username, password)
        val registeredUser = userService.register(request)

        // then
        then(registeredUser.id).isPositive
        then(registeredUser.email).isEqualTo(email.lowercase())
        then((registeredUser.username)).isEqualTo(username.lowercase())
        val savedUser = User(email = email, username = username, password = password, roles = hashSetOf(Role.USER))
        verify(userRepository).save(savedUser)
    }

    @Test
    fun `when the username is taken, a BadRequestException is thrown`() {
        // given
        val email = faker.email()
        val username = faker.username()
        val password = faker.password()
        given { userRepository.existsByUsername(any()) } willReturn { true }

        // when
        val request = UserAddRequest(email, username, password)
        val throwable = catchThrowable { userService.register(request) }

        // then
        then(throwable)
            .isInstanceOf(BadRequestException::class.java)
            .hasMessageContaining("username")
        verify(userRepository, never()).save(any())
    }

    @Test
    fun `when the email is taken, a BadRequestException is thrown`() {
        // given
        val email = faker.email()
        val username = faker.username()
        val password = faker.password()
        given { userRepository.existsByEmail(any()) } willReturn { true }

        // when
        val request = UserAddRequest(email, username, password)
        val throwable = catchThrowable { userService.register(request) }

        // then
        then(throwable)
            .isInstanceOf(BadRequestException::class.java)
            .hasMessageContaining("email")
        verify(userRepository, never()).save(any())
    }

    @Test
    fun `when user email is updated, it is saved to the database`() {
        // given
        val id = faker.randomLong()
        val newEmail = faker.email()
        val username = faker.username()
        val userInDb = User(
            id = id,
            username = username.lowercase(),
            email = faker.email().lowercase(),
            password = faker.password()
        )
        given { userRepository.findById(any()) } willReturn { Optional.of(userInDb) }
        given { userRepository.existsByEmail(any()) } willReturn { false }

        // when
        val request = UserUpdateRequest(newEmail, username)
        val updatedUser = userService.updateUser(id, request)

        // then
        then(updatedUser.email).isEqualTo(newEmail.lowercase())
    }
}

fun User.withId(id: Long) = User(
    id = id,
    username = username,
    email = email,
    password = password,
    roles = roles,
    qr = qr
)