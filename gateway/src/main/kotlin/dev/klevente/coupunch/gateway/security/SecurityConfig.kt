package dev.klevente.coupunch.gateway.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
            http
                    .authorizeExchange()
                    .anyExchange()
                    .authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .build()

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER", "ADMIN")
            .build()
        return MapReactiveUserDetailsService(user)
    }
}

@Configuration
// @EnableRedisWebSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@EnableRedisWebSession
class SessionConfig {
    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory()
}