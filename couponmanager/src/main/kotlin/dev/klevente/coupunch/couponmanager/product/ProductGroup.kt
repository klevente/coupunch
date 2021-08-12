package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.library.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "app_product_group")
class ProductGroup(

    id: Long = -1L,

    @Column(nullable = false)
    var name: String = "",

    @OneToMany(mappedBy = "group")
    var products: MutableSet<Product> = hashSetOf(),
) : BaseEntity(id) {
    override fun toString() = "ProductGroup($formattedId, name=$name)"
}