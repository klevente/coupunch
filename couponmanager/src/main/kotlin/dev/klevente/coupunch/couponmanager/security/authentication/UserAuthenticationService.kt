package dev.klevente.coupunch.couponmanager.security.authentication

import dev.klevente.coupunch.couponmanager.config.CompanyConfigService
import dev.klevente.coupunch.couponmanager.security.authorization.Role
import dev.klevente.coupunch.couponmanager.user.CompanyUser
import dev.klevente.coupunch.couponmanager.user.CompanyUserRepository
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.security.AuthUser
import org.slf4j.Logger
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAuthenticationService(
    private val log: Logger,
    private val companyUserRepository: CompanyUserRepository,
    private val companyConfigService: CompanyConfigService,
) : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val usernameLowercase = username.toLowerCase()
        val user = companyUserRepository.findFirstByUsername(usernameLowercase) ?:
            throw EntityNotFoundException(CompanyUser::class, "username", username)
        log.info("Login request for $username")

        return AuthUser.fromUser(user)
    }

    private fun AuthUser.Companion.fromUser(user: CompanyUser) = AuthUser(
        id = user.id,
        username = user.username,
        password = user.password,
        authorities = user.roles.map(Role::grantedAuthority).plus(getCompanyNameRole()).toSet()
    )

    private fun getCompanyNameRole() = SimpleGrantedAuthority("ROLE_${companyConfigService.getCompanyUrl().toUpperCase()}")
}