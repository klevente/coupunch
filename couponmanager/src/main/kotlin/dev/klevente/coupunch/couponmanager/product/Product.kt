package dev.klevente.coupunch.couponmanager.product

import javax.persistence.*

@Entity
@Table(name = "app_product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var price: Double = 0.0,

    @ManyToOne
    var group: ProductGroup,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false

        return true
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "Product(id=$id, name=$name, price=$price)"
}