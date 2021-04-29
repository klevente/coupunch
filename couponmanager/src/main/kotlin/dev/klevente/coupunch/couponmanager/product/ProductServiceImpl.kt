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

        val group = productGroupService.getProductGroup(request.group)

        val product = productRepository.save(
            Product(
                name = request.name,
                price = request.price,
                group = group
            )
        )

        group.products.add(product)

        return product
    }

    override fun getProductResponse(id: Long) = getProduct(id).toResponse()

    @Transactional
    override fun updateProduct(id: Long, request: ProductUpdateRequest) {
        val product = getProduct(id)

        val oldGroup = product.group
        val newGroup = productGroupService.getProductGroup(request.group)

        product.apply {
            name = request.name
            price = request.price
            group = newGroup
        }

        oldGroup.products.remove(product)
        newGroup.products.add(product)
    }

    @Transactional
    override fun deleteProduct(id: Long) {
        val product = getProduct(id)

        product.group.products.remove(product)

        productRepository.delete(product)
    }
}