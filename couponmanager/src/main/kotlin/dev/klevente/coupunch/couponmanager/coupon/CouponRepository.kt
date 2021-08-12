package dev.klevente.coupunch.couponmanager.coupon

import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
}