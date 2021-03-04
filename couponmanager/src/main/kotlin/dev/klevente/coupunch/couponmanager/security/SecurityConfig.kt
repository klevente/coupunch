package dev.klevente.coupunch.couponmanager.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer
import org.springframework.session.web.http.DefaultCookieSerializer
import javax.annotation.PostConstruct

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http {
            httpBasic {
                disable()
            }
            authorizeRequests {
                authorize("/coupons", permitAll)
                authorize("/actuator/health", permitAll)
                authorize(anyRequest, authenticated)
            }
            csrf {
                disable()
            }
        }
    }
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