package dev.klevente.coupunch.usermanager.user.company

import dev.klevente.coupunch.library.entity.BaseEntityWithSuppliedStringId
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "app_company")
class Company(
    id: String = "",

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var url: String = ""
) : BaseEntityWithSuppliedStringId(id) {
    override fun toString() = "Company($formattedId, name=$name, url=$url"
}