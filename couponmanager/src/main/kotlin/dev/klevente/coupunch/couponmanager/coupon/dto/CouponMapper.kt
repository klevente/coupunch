package dev.klevente.coupunch.couponmanager.coupon.dto

import dev.klevente.coupunch.couponmanager.coupon.*
import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup
import dev.klevente.coupunch.library.util.mapToArray
import dev.klevente.coupunch.library.util.toMutableMap

fun Collection<Coupon>.toResponse() = CouponsResponse(
    coupons = mapToArray { it.toResponse() }
)

fun Coupon.toResponse() = CouponResponse(
    id = id,
    name = name,
    eligibleItems = EligibleItemsResponse(
        products = eligibleProducts.toResponse(),
        productGroups = eligibleProductGroups.toResponse()
    ),
    type = type.toString(),
    rewards = rewards.toResponse()
)

fun EligibleProducts.toResponse() = mapToArray { (key, value) ->
    EligibleProductResponse(
        id = key.id,
        name = key.name,
        price = key.price,
        points = value
    )
}

fun EligibleProductGroups.toResponse() = mapToArray { (key, value) ->
    EligibleProductGroupResponse(
        id = key.id,
        name = key.name,
        points = value
    )
}

fun Collection<Reward>.toResponse() = mapToArray { it.toResponse() }

fun Reward.toResponse() = RewardResponse(
    threshold = threshold,
    discountType = discountType.toString(),
    discount = discount,
    products = products.toResponse(),
    productGroups = productGroups.toResponse()
)

fun ProductRewards.toResponse() = mapToArray { (key, value) ->
    ProductRewardResponse(
        id = key.id,
        name = key.name,
        originalPrice = key.price,
        amount = value
    )
}

fun ProductGroupRewards.toResponse() = mapToArray { (key, value) ->
    ProductGroupRewardResponse(
        id = key.id,
        name = key.name,
        amount = value
    )
}

fun Array<EligibleProductRequest>.toDomain(
    getProducts: (ids: List<Long>) -> List<Product>
): EligibleProducts {
    val ids = map { it.id }
    val products = getProducts(ids)
    return products.mapIndexed { index, product ->
        product to this[index].points
    }.toMutableMap()
}

fun Array<EligibleProductGroupRequest>.toDomain(
    getProductGroups: (ids: List<Long>) -> List<ProductGroup>
): EligibleProductGroups {
    val ids = map { it.id }
    val productGroups = getProductGroups(ids)
    return productGroups.mapIndexed { index, productGroup ->
        productGroup to this[index].points
    }.toMutableMap()
}

fun Array<ProductRewardRequest>.toDomain(
    getProducts: (ids: List<Long>) -> List<Product>
): ProductRewards {
    val ids = map { it.id }
    val products = getProducts(ids)
    return products.mapIndexed { index, product ->
        product to this[index].amount
    }.toMutableMap()
}

fun Array<ProductGroupRewardRequest>.toDomain(
    getProductGroups: (ids: List<Long>) -> List<ProductGroup>
): ProductGroupRewards {
    val ids = map { it.id }
    val productGroups = getProductGroups(ids)
    return productGroups.mapIndexed { index, productGroup ->
        productGroup to this[index].amount
    }.toMutableMap()
}

fun CouponCreateRequest.toCouponWithoutRewards(
    getProducts: (ids: List<Long>) -> List<Product>,
    getProductGroups: (ids: List<Long>) -> List<ProductGroup>
) = Coupon(
    name = name,
    type = type.toCouponType(),
    eligibleProducts = eligibleItems.products.toDomain(getProducts),
    eligibleProductGroups = eligibleItems.productGroups.toDomain(getProductGroups)
)

fun RewardRequest.toReward(
    owner: Coupon,
    getProducts: (ids: List<Long>) -> List<Product>,
    getProductGroups: (ids: List<Long>) -> List<ProductGroup>
) = Reward(
    threshold = threshold,
    discountType = discountType.toDiscountType(),
    discount = discount,
    products = products.toDomain(getProducts),
    productGroups = productGroups.toDomain(getProductGroups),
    coupon = owner
)