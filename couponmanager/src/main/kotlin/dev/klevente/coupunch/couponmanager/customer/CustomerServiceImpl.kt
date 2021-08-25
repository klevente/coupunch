package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.customer.dto.*
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CustomerServiceImpl(
    private val log: Logger,
    private val customerRepository: CustomerRepository
) : CustomerService {
    override fun getCustomer(id: Long) =
        customerRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(Customer::class, id)

    override fun getCustomerByUsername(username: String) =
        customerRepository.findByUsername(username) ?: throw EntityNotFoundException(Customer::class, "username", username)

    override fun getCompanyCustomers() = customerRepository.findAll().toResponse()

    @Transactional
    override fun addCustomer(request: CustomerCreateRequest): CustomerResponse {
        val customer = customerRepository.save(
            Customer(
                id = request.id,
                name = request.name,
                username = request.username,
                code = request.code
            )
        )

        return customer.toResponse()
    }

    @Transactional
    override fun updateCustomer(id: Long, request: CustomerUpdateRequest): CustomerResponse {
        val customer = getCustomer(id)

        customer.apply {
            name = request.name
            username = request.username
            code = request.code
        }

        return customer.toResponse()
    }

    override fun getCouponsForCustomer(username: String) = getCustomerByUsername(username).coupons.toResponse()

    override fun getCustomerFromQrCode(code: String): CustomerResponse {
        val customer = customerRepository.findByCode(code) ?: throw EntityNotFoundException(Customer::class, "code", code)
        return customer.toResponse()
    }
}