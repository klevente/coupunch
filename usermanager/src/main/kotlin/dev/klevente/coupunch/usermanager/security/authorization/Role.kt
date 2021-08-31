package dev.klevente.coupunch.usermanager.security.authorization

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role {
    USER, ADMIN;

    val grantedAuthority
        get() = SimpleGrantedAuthority("ROLE_$name")
}

