package dev.klevente.coupunch.couponmanager.user

import dev.klevente.coupunch.couponmanager.config.CompanyConfigService
import dev.klevente.coupunch.couponmanager.user.dto.CompanyUserAddRequest
import dev.klevente.coupunch.couponmanager.user.dto.CompanyUserResponse
import dev.klevente.coupunch.couponmanager.user.dto.toResponse
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.library.security.AuthenticationFacade
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CompanyUserServiceImpl(
    private val log: Logger,
    private val companyConfigService: CompanyConfigService,
    private val companyUserRepository: CompanyUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationFacade: AuthenticationFacade,
) : CompanyUserService {

    override fun getUser(id: Long) = companyUserRepository.findByIdOrNull(id) ?:
        throw EntityNotFoundException.byId(CompanyUser::class, id)

    override fun getCurrentUser() = getUser(authenticationFacade.userId)

    override fun getUserResponse(id: Long) = getUser(id).toResponse(
        companyName = companyConfigService.getCompanyName(),
        companyUrl = companyConfigService.getCompanyUrl()
    )

    override fun getCurrentUserResponse() = getCurrentUser().toResponse(
        companyName = companyConfigService.getCompanyName(),
        companyUrl = companyConfigService.getCompanyUrl()
    )

    override fun register(request: CompanyUserAddRequest): CompanyUser {
        log.info("Registering ${request.username}: (${authenticationFacade.remoteAddress})")
        val usernameLowercase = request.username.toLowerCase()

        val passwordHashed = passwordEncoder.encode(request.password)

        val user = companyUserRepository.save(
            CompanyUser(
                username = usernameLowercase,
                password = passwordHashed
            )
        )

        log.info("Registration successful for $usernameLowercase")

        return user
    }
}