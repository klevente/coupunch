package dev.klevente.coupunch.couponmanager.security.authorization

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role {
    COMPANY_USER, COMPANY_ADMIN, COMPANY_MASTER;

    val grantedAuthority: SimpleGrantedAuthority
        get() = SimpleGrantedAuthority("ROLE_${name}")
}