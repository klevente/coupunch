package dev.klevente.coupunch.couponmanager.user

import dev.klevente.coupunch.couponmanager.security.authorization.Role
import javax.persistence.*

@Entity
@Table(name = "app_company_user")
data class CompanyUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ElementCollection(targetClass = Role::class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = mutableSetOf(Role.COMPANY_USER)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompanyUser

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "CompanyUser(id=$id, username='$username')"
}