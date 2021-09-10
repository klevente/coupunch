package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup

interface CouponService {
    fun getCoupon(id: Long): Coupon

    fun removeDeletedProductFromCoupons(product: Product)

    fun removeDeletedProductGroupFromCoupons(productGroup: ProductGroup)

    fun getRelevantCouponsFor(product: Product): Set<Coupon>

    fun getCouponsNotIn(coupons: Collection<Coupon>): List<Coupon>
}