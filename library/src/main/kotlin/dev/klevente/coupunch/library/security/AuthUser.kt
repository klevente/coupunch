package dev.klevente.coupunch.library.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthUser(
    private val id: Long,
    private val username: String,
    private val password: String,
    private val authorities: Set<GrantedAuthority>
) : UserDetails {

    // placeholder for creating static extension functions for this class
    companion object

    fun getId(): Long = id

    override fun getUsername(): String = username

    override fun getPassword(): String = password

    override fun getAuthorities(): Set<GrantedAuthority> = authorities

    override fun isEnabled(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}