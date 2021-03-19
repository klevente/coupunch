package dev.klevente.coupunch.couponmanager.product

import javax.persistence.*

@Entity
@Table(name = "app_product_group")
data class ProductGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,

    @Column(nullable = false)
    var name: String = "",

    @ManyToMany
    var products: MutableSet<Product> = hashSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductGroup

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "ProductGroup(id=$id, name=$name)"
}