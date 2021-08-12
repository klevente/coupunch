package dev.klevente.coupunch.couponmanager.coupon

enum class CouponType {
    POINT, PRICE;

    companion object {
        fun from(str: String) = when (str.lowercase()) {
            "point" -> POINT
            "price" -> PRICE
            else -> throw IllegalArgumentException("$str is not a valid CouponType!")
        }
    }
}

fun String.toCouponType() = CouponType.from(this)