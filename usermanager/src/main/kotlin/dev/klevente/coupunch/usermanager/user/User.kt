package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.library.entity.BaseEntity
import dev.klevente.coupunch.usermanager.security.authorization.Role
import dev.klevente.coupunch.usermanager.user.company.Company
import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(

    id: Long = -1L,

    @Column(nullable = false, unique = true)
    var username: String = "",

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false, unique = true)
    var code: String = "",

    @ElementCollection(targetClass = Role::class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = mutableSetOf(Role.USER),

    @Lob
    var qr: ByteArray? = null,

    @ManyToMany
    var companies: MutableSet<Company> = mutableSetOf()
) : BaseEntity(id) {
    override fun toString() = "User($formattedId, username=$username, email=$email, roles=$roles)"
}