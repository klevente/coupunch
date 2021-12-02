package dev.klevente.coupunch.couponmanager.customer

import dev.klevente.coupunch.couponmanager.coupon.CouponService
import dev.klevente.coupunch.couponmanager.customer.dto.*
import dev.klevente.coupunch.library.exception.EntityNotFoundException
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.AbstractMap.SimpleEntry

@Service
@Transactional(readOnly = true)
class CustomerServiceImpl(
    private val log: Logger,
    private val customerRepository: CustomerRepository,
    private val couponService: CouponService,
    private val customerEventPublisher: CustomerEventPublisher
) : CustomerActions, CustomerEvents, CustomerService {
    override fun getCustomer(id: Long) =
        customerRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(Customer::class, id)

    override fun getCustomerByUsername(username: String) =
        customerRepository.findByUsername(username) ?: throw EntityNotFoundException(
            Customer::class,
            "username",
            username
        )

    override fun getCompanyCustomers() = customerRepository.findAll().toUserResponse()

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

        customerEventPublisher.customerAddedToCompany(customer)

        return customer.toUserResponse()
    }

    @Transactional
    override fun updateCustomer(id: Long, request: CustomerUpdateRequest): CustomerResponse {
        val customer = getCustomer(id)

        customer.apply {
            name = request.name
            username = request.username
            code = request.code
        }

        return customer.toUserResponse()
    }

    @Transactional
    override fun update(event: UserUpdateEvent) {
        val customer = getCustomer(event.id)

        customer.apply {
            name = event.name
            username = event.username
            code = event.code
        }
    }

    override fun getCouponsForCustomer(username: String) = getCustomerByUsername(username).coupons.toResponse()

    override fun getCustomerByCode(code: String): CustomerResponse {
        val customer =
            customerRepository.findByCode(code) ?: throw EntityNotFoundException(Customer::class, "code", code)
        return customer.toUserResponse()
    }

    override fun getCouponsForUser(userId: Long): UserCouponsResponse {
        val customer = getCustomer(userId)
        val coupons = HashMap(customer.coupons) // create copy so that JPA does not persist
        val otherCoupons = couponService.getCouponsNotIn(coupons.keys)
        otherCoupons.forEach {
            coupons.putIfAbsent(it, 0.0.toBigDecimal())
        }

        return coupons.toUserResponse()
    }

    override fun getCouponForUser(userId: Long, couponId: Long): UserCouponResponse {
        val customer = getCustomer(userId)
        val coupon = customer.coupons
            .filterKeys { it.id == couponId }
            .entries
            .firstOrNull()
            ?: couponService
                .getCoupon(couponId)
                .let {
                    SimpleEntry(it, 0.0.toBigDecimal())
                }

        return coupon.toUserResponse()
    }

    override fun resendUserAddedToCompanyEvent(username: String) {
        val customer = getCustomerByUsername(username)
        customerEventPublisher.customerAddedToCompany(customer)
    }
}