package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.library.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "app_product")
class Product(

    id: Long = -1L,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var price: Double = 0.0,

    @ManyToOne
    var group: ProductGroup? = null,
) : BaseEntity(id) {
    override fun toString() = "Product($formattedId, name=$name, price=$price)"
}