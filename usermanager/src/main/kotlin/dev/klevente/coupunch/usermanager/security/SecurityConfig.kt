package dev.klevente.coupunch.usermanager.security

import dev.klevente.coupunch.usermanager.security.authentication.UserAuthenticationService
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer
import org.springframework.session.web.http.DefaultCookieSerializer
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val logger: Logger,
    private val passwordEncoder: PasswordEncoder,
    private val userAuthenticationService: UserAuthenticationService,
) : WebSecurityConfigurerAdapter() {


    private val redirectUrl: String = "http://localhost:8000/coupons/34"

    override fun configure(http: HttpSecurity) {
        http {
            csrf {
                disable()
            }
            authorizeRequests {
                authorize("/register", permitAll)
                authorize("/actuator/health", permitAll)
                authorize(anyRequest, authenticated)
            }
            formLogin {
                loginPage = "/login"
                loginProcessingUrl = "/api/users/login"
                defaultSuccessUrl(redirectUrl, true)
                permitAll()

            }
            logout {
                logoutUrl = "/api/users/logout"
                clearAuthentication = true
                invalidateHttpSession = true
                deleteCookies("SESSION", "XSRF-TOKEN")
                logoutSuccessUrl = "/login"
            }
            exceptionHandling {
                authenticationEntryPoint = AuthenticationEntryPoint { httpServletRequest, httpServletResponse, authenticationException ->
                    authenticationException?.let {
                        logger.info("Invalid authentication request for ${httpServletRequest.getParameter("username")} from ${httpServletRequest.remoteAddr}")
                        httpServletResponse.status = HttpServletResponse.SC_UNAUTHORIZED
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