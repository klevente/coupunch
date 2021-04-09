package dev.klevente.coupunch.usermanager.security

import com.fasterxml.jackson.databind.ObjectMapper
import dev.klevente.coupunch.library.security.AuthUser
import dev.klevente.coupunch.library.util.Status
import dev.klevente.coupunch.usermanager.security.authentication.UserAuthenticationService
import dev.klevente.coupunch.usermanager.user.UserRepository
import dev.klevente.coupunch.usermanager.user.UserService
import dev.klevente.coupunch.usermanager.user.dto.toResponse
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType.*
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer
import org.springframework.session.web.http.DefaultCookieSerializer
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val log: Logger,
    private val passwordEncoder: PasswordEncoder,
    private val userAuthenticationService: UserAuthenticationService,
    private val userService: UserService
) : WebSecurityConfigurerAdapter() {

    private val objectMapper = ObjectMapper()

    override fun configure(http: HttpSecurity) {
        http {
            csrf {
                disable()
            }
            authorizeRequests {
                authorize("/register", permitAll)
                authorize(POST, "/api/users", permitAll)
                authorize("/actuator/health", permitAll)
                authorize(anyRequest, authenticated)
            }
            formLogin {
                loginPage = "/login"
                loginProcessingUrl = "/api/users/login"
                // defaultSuccessUrl(redirectUrl, true)
                authenticationSuccessHandler = AuthenticationSuccessHandler { httpServletRequest, httpServletResponse, authentication ->
                    val authUser = authentication.principal as AuthUser
                    val user = userService.getUserResponse(authUser.getId())
                    httpServletResponse.writeJson(OK, user)
                }
                authenticationFailureHandler = AuthenticationFailureHandler { httpServletRequest, httpServletResponse, authenticationException ->
                    httpServletResponse.writeJson(FORBIDDEN, Status("Login failed!"))
                }
                permitAll()
            }
            logout {
                logoutUrl = "/api/users/logout"
                clearAuthentication = true
                invalidateHttpSession = true
                deleteCookies("SESSION", "XSRF-TOKEN")
                // logoutSuccessUrl = "/login"
                logoutSuccessHandler = LogoutSuccessHandler { httpServletRequest, httpServletResponse, authentication ->
                    httpServletResponse.writeJson(OK, Status("Logout successful!"))
                }
            }
            exceptionHandling {
                authenticationEntryPoint = AuthenticationEntryPoint { httpServletRequest, httpServletResponse, authenticationException ->
                    authenticationException?.let {
                        log.info("Invalid authentication request for ${httpServletRequest.getParameter("username")} from ${httpServletRequest.remoteAddr}")
                        httpServletResponse.writeJson(UNAUTHORIZED, Status("Unauthorized!"))
                    }
                }
            }
            httpBasic {
                disable()
            }
        }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        return DaoAuthenticationProvider().apply {
            setPasswordEncoder(passwordEncoder)
            setUserDetailsService(userAuthenticationService)
        }
    }

    @Bean
    fun httpSessionEventPublisher() = HttpSessionEventPublisher()

    private fun HttpServletResponse.writeJson(httpStatus: HttpStatus, response: Any): HttpServletResponse {
        return apply {
            status = httpStatus.value()
            contentType = APPLICATION_JSON_VALUE
            characterEncoding = "utf-8"
            writer.print(response.toJson())
        }
    }

    private fun Any.toJson(): String = objectMapper.writeValueAsString(this)
}

@Configuration
@EnableRedisHttpSession
class SessionConfig(
    private val defaultCookieSerializer: DefaultCookieSerializer
) : AbstractHttpSessionApplicationInitializer() {
    /*
     * This is needed because MVC encodes session IDs in base64, while Webflux does not
     */
    @PostConstruct
    fun init() {
        defaultCookieSerializer.setUseBase64Encoding(false)
    }
}