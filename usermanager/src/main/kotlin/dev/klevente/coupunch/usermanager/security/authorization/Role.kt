package dev.klevente.coupunch.usermanager.security.authorization

import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "app_role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,

    @Column(nullable = false, unique = true)
    var name: String = "",
) {
    val grantedAuthority: SimpleGrantedAuthority
        get() = SimpleGrantedAuthority("ROLE_$name")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "Role(id=$id, name='$name')"
}