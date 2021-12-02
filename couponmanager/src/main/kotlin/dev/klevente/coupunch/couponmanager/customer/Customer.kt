package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.coupon.Coupon
import dev.klevente.coupunch.library.entity.BaseEntityWithSuppliedId
import java.math.BigDecimal
import javax.persistence.*

typealias CustomerCoupons = MutableMap<Coupon, BigDecimal>

@Entity
@Table(name = "app_customer")
class Customer(

    id: Long = -1L,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false)
    var code: String = "",

    @ElementCollection
    @CollectionTable(name = "app_user_coupons")
    var coupons: CustomerCoupons = mutableMapOf()
) : BaseEntityWithSuppliedId(id) {
    override fun toString() = "Customer($formattedId, name=$name, username=$username)"
}