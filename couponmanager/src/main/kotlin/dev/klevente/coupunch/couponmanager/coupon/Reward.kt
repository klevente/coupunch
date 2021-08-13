package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup
import dev.klevente.coupunch.library.entity.BaseEntity
import javax.persistence.*

typealias ProductRewards = MutableMap<Product, Int>
typealias ProductGroupRewards = MutableMap<ProductGroup, Int>

@Entity
@Table(name = "app_reward")
class Reward(

    id: Long = -1L,

    @Column(nullable = false)
    var threshold: Double = 0.0,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var discountType: DiscountType = DiscountType.FIXED,

    @Column(nullable = false)
    var discount: Double = 0.0,

    @ElementCollection
    @CollectionTable(name = "app_product_reward")
    var products: ProductRewards = mutableMapOf(),

    @ElementCollection
    @CollectionTable(name = "app_product_group_reward")
    var productGroups: ProductGroupRewards = mutableMapOf(),

    @ManyToOne
    var coupon: Coupon
) : BaseEntity(id) {
    override fun toString() = "Reward($formattedId, threshold=$threshold, discountType=$discountType, discount=$discount)"
}