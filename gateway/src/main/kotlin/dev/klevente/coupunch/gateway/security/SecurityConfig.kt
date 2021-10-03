package dev.klevente.coupunch.gateway.security

import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val log: Logger
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .httpBasic().authenticationEntryPoint { exchange, exe ->
                Mono.fromRunnable { exchange.response.statusCode = HttpStatus.UNAUTHORIZED }
            }
            .and()
            .csrf().disable()
            .authorizeExchange()
            // .pathMatchers("/login", "/api/users/**", "/register", "/coupons", "/favicon.ico", "/ui/**").permitAll()
            // .pathMatchers("/actuator/health").permitAll()
            .anyExchange()
            // .authenticated()
            .permitAll()
            .and()
            .build()

    /*@Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER", "ADMIN")
            .build()
        return MapReactiveUserDetailsService(user)
    }*/
}

@Configuration
// @EnableRedisWebSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@EnableRedisWebSession
class SessionConfig {
    /*@Bean
    fun redisConnectionFactory() = LettuceConnectionFactory()*/
}