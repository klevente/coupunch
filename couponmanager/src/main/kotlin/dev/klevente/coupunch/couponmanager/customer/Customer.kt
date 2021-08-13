package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.coupon.Coupon
import dev.klevente.coupunch.library.entity.BaseEntity
import javax.persistence.*

typealias UserCoupons = MutableMap<Coupon, Double>

@Entity
@Table(name = "app_customer")
class Customer(

    id: Long = -1L,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var code: String = "",

    @ElementCollection
    @CollectionTable(name = "app_user_coupons")
    var coupons: UserCoupons = mutableMapOf()
) : BaseEntity() {
    override fun toString() = "Customer($formattedId)"
}