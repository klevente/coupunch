package dev.klevente.coupunch.couponmanager.user

import dev.klevente.coupunch.couponmanager.security.authorization.Role
import dev.klevente.coupunch.library.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "app_company_user")
class CompanyUser(

    id: Long = -1L,

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ElementCollection(targetClass = Role::class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = mutableSetOf(Role.COMPANY_USER)
) : BaseEntity(id) {
    override fun toString() = "CompanyUser($formattedId, username=$username)"
}