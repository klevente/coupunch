package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.coupon.dto.CouponCreateRequest
import dev.klevente.coupunch.couponmanager.coupon.dto.CouponUpdateRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/coupons")
class CouponController(
    private val couponActions: CouponActions
) {
    @GetMapping
    fun getCoupons() = ok(couponActions.getCouponsResponse())

    @GetMapping("{couponId}")
    fun getCoupon(
        @PathVariable couponId: Long
    ) = ok(couponActions.getCouponResponse(couponId))

    @PostMapping
    fun addCoupon(
        @RequestBody @Valid request: CouponCreateRequest
    ) = ok(couponActions.addCoupon(request))

    @PutMapping("{couponId}")
    fun updateCoupon(
        @PathVariable couponId: Long,
        @RequestBody @Valid request: CouponUpdateRequest
    ) = ok(couponActions.updateCoupon(couponId, request))

    @DeleteMapping("{couponId}")
    fun deleteCoupon(
        @PathVariable couponId: Long
    ) = ok(couponActions.deleteCoupon(couponId))
}