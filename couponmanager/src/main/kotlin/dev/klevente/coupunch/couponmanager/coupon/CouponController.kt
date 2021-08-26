package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.coupon.dto.CouponCreateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coupons")
class CouponController(
    private val couponActions: CouponActions
) {
    @GetMapping
    fun getCoupons() = couponActions.getCouponsResponse()

    @GetMapping("{couponId}")
    fun getCoupon(
        @PathVariable couponId: Long
    ) = couponActions.getCouponResponse(couponId)

    @PostMapping
    fun addCoupon(
        @RequestBody request: CouponCreateRequest
    ) = couponActions.addCoupon(request)

    @PutMapping("{couponId}")
    fun updateCoupon(
        @PathVariable couponId: Long,
        @RequestBody request: CouponUpdateRequest
    ) = couponActions.updateCoupon(couponId, request)

    @DeleteMapping("{couponId}")
    fun deleteCoupon(
        @PathVariable couponId: Long
    ) = couponActions.deleteCoupon(couponId)
}