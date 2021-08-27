package dev.klevente.coupunch.couponmanager.coupon

import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CouponRepository : JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c join c.eligibleProducts p " +
           "where ?1 in (key(p))")
    fun findCouponsWithEligibleProduct(product: Product): Set<Coupon>

    @Query("select c from Coupon c join c.eligibleProductGroups g " +
           "where ?1 in (key(g))")
    fun findCouponsWithEligibleProductGroup(productGroup: ProductGroup): Set<Coupon>

    @Query("select c from Coupon c join c.rewards r join r.products p " +
           "where ?1 in (key(p))")
    fun findCouponsWithRewardProduct(product: Product): Set<Coupon>

    @Query("select c from Coupon c join c.rewards r join r.productGroups g " +
            "where ?1 in (key(g))")
    fun findCouponsWithRewardProductGroup(productGroup: ProductGroup): Set<Coupon>

    /*@Query("select c from Coupon c join c.eligibleProductGroups g join (key(g)).products p " +
            "where ?1 = p")*/
    /*@Query("select c from Coupon c join c.eligibleProductGroups g join  ")*/
    // fun findCouponsWithEligibleProductInEligibleProductGroups(product: Product): Set<Coupon>
}