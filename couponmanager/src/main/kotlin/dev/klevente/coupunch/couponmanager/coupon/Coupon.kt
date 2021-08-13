package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup
import dev.klevente.coupunch.library.entity.BaseEntity
import javax.persistence.*

typealias EligibleProducts = MutableMap<Product, Int?>
typealias EligibleProductGroups = MutableMap<ProductGroup, Int?>

@Entity
@Table(name = "app_coupon")
class Coupon(

    id: Long = -1L,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: CouponType = CouponType.POINT,

    @ElementCollection
    @CollectionTable(name = "app_eligible_product")
    var eligibleProducts: EligibleProducts = mutableMapOf(),

    @ElementCollection
    @CollectionTable(name = "app_eligible_product_group")
    var eligibleProductGroups: EligibleProductGroups = mutableMapOf(),

    @OneToMany(mappedBy = "coupon", cascade = [CascadeType.REMOVE])
    var rewards: MutableList<Reward> = mutableListOf()
) : BaseEntity(id) {
    override fun toString() = "Coupon($formattedId, name=$name, type=$type)"
}
