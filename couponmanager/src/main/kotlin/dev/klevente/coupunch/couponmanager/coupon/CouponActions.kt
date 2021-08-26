package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.coupon.dto.CouponCreateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponResponse
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponUpdateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponsResponse

interface CouponActions {
    fun getCouponResponse(id: Long): CouponResponse

    fun getCouponsResponse(): CouponsResponse

    fun addCoupon(request: CouponCreateRequest): CouponResponse

    fun updateCoupon(id: Long, request: CouponUpdateRequest): CouponResponse

    fun deleteCoupon(id: Long): CouponResponse
}