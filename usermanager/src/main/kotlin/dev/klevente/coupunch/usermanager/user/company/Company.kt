package dev.klevente.coupunch.usermanager.user.company

import dev.klevente.coupunch.library.entity.BaseEntityWithSuppliedId
import javax.persistence.*

@Entity
@Table(name = "app_company")
class Company(
    id: Long = -1,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var url: String = ""
) : BaseEntityWithSuppliedId(id) {
    override fun toString() = "Company($formattedId, name=$name, url=$url"
}