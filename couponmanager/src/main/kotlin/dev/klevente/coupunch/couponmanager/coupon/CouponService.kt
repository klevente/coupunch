package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.coupon.dto.CouponCreateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponResponse
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponUpdateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponsResponse

interface CouponService {
    fun getCoupon(id: Long): Coupon

    fun addCoupon(request: CouponCreateRequest): Coupon

    fun updateCoupon(id: Long, request: CouponUpdateRequest)

    fun deleteCoupon(id: Long)

    fun getCouponResponse(id: Long): CouponResponse

    fun getCouponsResponse(): CouponsResponse
}