package dev.klevente.coupunch.couponmanager.security

import com.fasterxml.jackson.databind.ObjectMapper
import dev.klevente.coupunch.couponmanager.security.authentication.UserAuthenticationService
import dev.klevente.coupunch.couponmanager.user.CompanyUserService
import dev.klevente.coupunch.library.security.AuthUser
import dev.klevente.coupunch.library.util.Status
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
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
    private val companyUserService: CompanyUserService,
) : WebSecurityConfigurerAdapter() {

    private val objectMapper = ObjectMapper()

    override fun configure(http: HttpSecurity) {
        http {
            httpBasic {
                disable()
            }
            csrf {
                disable()
            }
            authorizeRequests {
                authorize("/coupons", permitAll)
                authorize("/actuator/health", permitAll)
                authorize(anyRequest, authenticated)
            }
            formLogin {
                loginProcessingUrl = "/users/login"
                authenticationSuccessHandler = AuthenticationSuccessHandler { request, response, authentication ->
                    val authUser = authentication.principal as AuthUser
                    val user = companyUserService.getUserResponse(authUser.getId())
                    response.writeJson(OK, user)
                }
                authenticationFailureHandler = AuthenticationFailureHandler { request, response, exception ->
                    response.writeJson(FORBIDDEN, Status("Login failed!"))
                }
                permitAll()
            }
            logout {
                logoutUrl = "/users/logout"
                clearAuthentication = true
                invalidateHttpSession = true
                deleteCookies("SESSION, XSRF-TOKEN")
                logoutSuccessHandler = LogoutSuccessHandler { request, response, authentication ->
                    response.writeJson(OK, Status("Logout successful!"))
                }
            }
            exceptionHandling {
                authenticationEntryPoint = AuthenticationEntryPoint { request, response, exception ->
                    exception?.let {
                        log.info("Invalid authentication request for ${request.getParameter("username")} from ${request.remoteAddr}")
                        response.writeJson(UNAUTHORIZED, Status("Unauthorized!"))
                    }
                }
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
            contentType = MediaType.APPLICATION_JSON_VALUE
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

@Configuration
// @Conditional(SecurityEnabledCondition::class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class MethodSecurityConfig