package dev.klevente.coupunch.usermanager.config

import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.User
import dev.klevente.coupunch.usermanager.user.UserRepository
import dev.klevente.coupunch.usermanager.user.company.Company
import dev.klevente.coupunch.usermanager.user.company.CompanyRepository
import dev.klevente.coupunch.usermanager.util.uuid
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InMemoryDatabaseInitializer(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val companyRepository: CompanyRepository
) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments) {

        if (userRepository.count() > 0L) {
            return
        }

        /*val couponManager = companyRepository.save(
            Company(
                id = "couponmanager",
                name = "Coupon Manager",
                url = "couponmanager"
            )
        )*/
        val user1 = userRepository.save(
            User(
                username = "johndoe12",
                name = "John Doe",
                email = "johndoe12@abc.com",
                password = passwordEncoder.encode("password"),
                code = "aa-aa", // uuid(),
                roles = mutableSetOf(Role.USER),
                companies = mutableSetOf()
            )
        )
        val user2 = userRepository.save(
            User(
                username = "billysmith98",
                name = "Bill Smith",
                email = "billsmith98@abc.com",
                password = passwordEncoder.encode("password"),
                code = "bb-bb", // uuid(),
                roles = mutableSetOf(Role.USER)
            )
        )
        val user3 = userRepository.save(
            User(
                username = "jane_h",
                name = "Jane Hoynen",
                email = "jane_h@abc.com",
                password = passwordEncoder.encode("password"),
                code = "cc-cc", // uuid(),
                roles = mutableSetOf(Role.USER)
            )
        )
        val user4 = userRepository.save(
            User(
                username = "robbw1",
                name = "Robert Wright",
                email = "robbw1@abc.com",
                password = passwordEncoder.encode("password"),
                code = uuid(),
                roles = mutableSetOf(Role.USER)
            )
        )
        val user5 = userRepository.save(
            User(
                username = "shivvih",
                name = "Siobhan Hoover",
                email = "shivvih@abc.com",
                password = passwordEncoder.encode("password"),
                code = uuid(),
                roles = mutableSetOf(Role.USER)
            )
        )
        val user6 = userRepository.save(
            User(
                username = "ahend__98",
                name = "Alex Hendricks",
                email = "ahend__98@abc.com",
                password = passwordEncoder.encode("password"),
                code = uuid(),
                roles = mutableSetOf(Role.USER)
            )
        )
    }
}