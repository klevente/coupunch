package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
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
) : ProductService {
    override fun getProduct(id: Long) =
        productRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(Product::class, id)

    @Transactional
    override fun addProduct(request: ProductCreateRequest): Product {

        val groups =
            productGroupService
                .getGroupsByIds(request.groups.toList()) + productGroupService.getDefaultGroup()

        val product = productRepository.save(
            Product(
                name = request.name,
                price = request.price,
                groups = groups.toMutableSet(),
            )
        )

        groups.forEach { it.products.add(product) }

        return product
    }

    override fun getProductResponse(id: Long) = getProduct(id).toResponse()

    @Transactional
    override fun updateProduct(id: Long, request: ProductUpdateRequest) {
        val product = getProduct(id)
        val defaultGroup = productGroupService.getDefaultGroup()
        val newGroups = productGroupService
            .getGroupsByIds(request.groups.toList())
            .let {
                if (!it.contains(defaultGroup)) {
                    it + defaultGroup
                } else {
                    it
                }
            }

        product.apply {
            name = request.name
            price = request.price
            groups = newGroups.toMutableSet()
        }

        newGroups.forEach { it.products.add(product) }
    }

    @Transactional
    override fun deleteProduct(id: Long) {
        val product = getProduct(id)

        product.groups.forEach {
            it.products.remove(product)
        }

        productRepository.delete(product)
    }
}