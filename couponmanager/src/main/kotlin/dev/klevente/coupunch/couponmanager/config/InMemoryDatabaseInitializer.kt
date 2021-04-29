package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.couponmanager.product.ProductGroup
import dev.klevente.coupunch.couponmanager.product.ProductGroupRepository
import dev.klevente.coupunch.couponmanager.security.authorization.Role
import dev.klevente.coupunch.couponmanager.user.CompanyUser
import dev.klevente.coupunch.couponmanager.user.CompanyUserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InMemoryDatabaseInitializer(
    private val companyUserRepository: CompanyUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val productGroupRepository: ProductGroupRepository,
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments) {
        val user = companyUserRepository.save(
            CompanyUser(
                username = "user",
                password = passwordEncoder.encode("password"),
                roles = mutableSetOf(Role.COMPANY_USER)
            )
        )
    }
}