package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.coupon.dto.CouponCreateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponResponse
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponUpdateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponsResponse
import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup

interface CouponService {
    fun getCoupon(id: Long): Coupon

    fun addCoupon(request: CouponCreateRequest): CouponResponse

    fun updateCoupon(id: Long, request: CouponUpdateRequest): CouponResponse

    fun deleteCoupon(id: Long): CouponResponse

    fun getCouponResponse(id: Long): CouponResponse

    fun getCouponsResponse(): CouponsResponse

    fun removeDeletedProductFromCoupons(product: Product)

    fun removeDeletedProductGroupFromCoupons(productGroup: ProductGroup)

    fun getRelevantCouponsFor(product: Product): Set<Coupon>
}