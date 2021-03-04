package dev.klevente.coupunch.usermanager.security.authorization

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RoleService(
    private val roleRepository: RoleRepository
) {
    val USER: Role by lazy {
        roleRepository.findFirstByName("USER")!!
    }
    val ADMIN: Role by lazy {
        roleRepository.findFirstByName("ADMIN")!!
    }
}

@Component
@Order(1)
class RoleInitializer(
    private val roleRepository: RoleRepository
) : ApplicationRunner {

    private val roles = arrayOf("USER", "ADMIN")

    @Transactional
    override fun run(args: ApplicationArguments?) {
        roles.forEach { role ->
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(Role(name = role))
            }
        }
    }
}