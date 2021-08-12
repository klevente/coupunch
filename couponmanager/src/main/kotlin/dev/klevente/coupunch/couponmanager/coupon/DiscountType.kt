package dev.klevente.coupunch.couponmanager.coupon

enum class DiscountType {
    FIXED, PERCENTAGE;

    companion object {
        fun from(str: String) = when(str.lowercase()) {
            "fixed" -> FIXED
            "percentage" -> PERCENTAGE
            else -> throw IllegalArgumentException("$str is not a valid DiscountType!")
        }
    }
}

fun String.toDiscountType() = DiscountType.from(this)