package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.coupon.CouponService
import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductResponse
import dev.klevente.coupunch.couponmanager.product.dto.ProductUpdateRequest
import dev.klevente.coupunch.couponmanager.product.dto.toResponse
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductServiceImpl(
    private val log: Logger,
    private val productRepository: ProductRepository,
    private val productGroupService: ProductGroupService,
    private val couponService: CouponService
) : ProductActions, ProductService {
    override fun getProduct(id: Long) =
        productRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(Product::class, id)

    @Transactional
    override fun addProduct(request: ProductCreateRequest): ProductResponse {

        val group = request.group?.let { productGroupService.getProductGroup(it) }

        val product = productRepository.save(
            Product(
                name = request.name,
                price = request.price,
                group = group
            )
        )

        group?.products?.add(product)

        return product.toResponse()
    }

    @Transactional
    override fun updateProduct(id: Long, request: ProductUpdateRequest): ProductResponse {
        val product = getProduct(id)

        val oldGroup = product.group
        val newGroup = request.group?.let { productGroupService.getProductGroup(it) }

        product.apply {
            name = request.name
            price = request.price
            group = newGroup
        }

        oldGroup?.products?.remove(product)
        newGroup?.products?.add(product)

        return product.toResponse()
    }

    @Transactional
    override fun deleteProduct(id: Long): ProductResponse {
        val product = getProduct(id)
        val ret = product.toResponse()

        couponService.removeDeletedProductFromCoupons(product)

        product.group?.products?.remove(product)
        productRepository.delete(product)

        return ret
    }

    override fun getProductResponse(id: Long) = getProduct(id).toResponse()

    override fun getProductsResponse() = productRepository.findAll().toResponse()
}