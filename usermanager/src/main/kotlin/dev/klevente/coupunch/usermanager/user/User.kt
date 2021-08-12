package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.library.entity.BaseEntity
import dev.klevente.coupunch.usermanager.security.authorization.Role
import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(

    id: Long = -1L,

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ManyToMany
    @JoinTable(name = "app_user_role")
    var roles: MutableSet<Role> = hashSetOf(),

    @Lob
    var qr: ByteArray? = null,
) : BaseEntity(id) {
    override fun toString() = "User($formattedId, username=$username, email=$email, roles=$roles)"
}