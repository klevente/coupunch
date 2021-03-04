package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.security.authorization.Role
import javax.persistence.*

@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ManyToMany
    @JoinTable(name = "app_user_role")
    var roles: MutableSet<Role> = hashSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "User(id=$id, username='$username', email='$email', roles=$roles)"
}