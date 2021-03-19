package dev.klevente.coupunch.couponmanager.product

import dev.klevente.coupunch.couponmanager.product.dto.ProductCreateRequest
import dev.klevente.coupunch.couponmanager.product.dto.ProductUpdateRequest
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

    override fun addProduct(request: ProductCreateRequest): Product {

        val groups = productGroupService.getProductsByIds(request.groups.toList())

        val product = productRepository.save(
            Product(
                name = request.name,
                price = request.price,
                groups = groups.toMutableSet(),
            )
        )

        return product
    }

    override fun getProductResponse(id: Long) {
        TODO("Not yet implemented")
    }

    override fun updateProduct(id: Long, request: ProductUpdateRequest) {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(id: Long) {
        TODO("Not yet implemented")
    }
}