package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.coupon.dto.CouponCreateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coupons")
class CouponController(
    private val couponService: CouponService
) {
    @GetMapping
    fun getCoupons() = couponService.getCouponsResponse()

    @GetMapping("{couponId}")
    fun getCoupon(
        @PathVariable couponId: Long
    ) = couponService.getCouponResponse(couponId)

    @PostMapping
    fun addCoupon(
        @RequestBody request: CouponCreateRequest
    ) = couponService.addCoupon(request)

    @PutMapping("{couponId}")
    fun updateCoupon(
        @PathVariable couponId: Long,
        @RequestBody request: CouponUpdateRequest
    ) = couponService.updateCoupon(couponId, request)

    @DeleteMapping("{couponId}")
    fun deleteCoupon(
        @PathVariable couponId: Long
    ) = couponService.deleteCoupon(couponId)
}