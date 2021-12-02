package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.couponmanager.coupon.*
import dev.klevente.coupunch.couponmanager.customer.Customer
import dev.klevente.coupunch.couponmanager.customer.CustomerRepository
import dev.klevente.coupunch.couponmanager.product.Product
import dev.klevente.coupunch.couponmanager.product.ProductGroup
import dev.klevente.coupunch.couponmanager.product.ProductGroupRepository
import dev.klevente.coupunch.couponmanager.product.ProductRepository
import dev.klevente.coupunch.couponmanager.security.authorization.Role
import dev.klevente.coupunch.couponmanager.user.CompanyUser
import dev.klevente.coupunch.couponmanager.user.CompanyUserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InMemoryDatabaseInitializer(
    private val companyUserRepository: CompanyUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val productRepository: ProductRepository,
    private val productGroupRepository: ProductGroupRepository,
    private val couponRepository: CouponRepository,
    private val rewardRepository: RewardRepository,
    private val customerRepository: CustomerRepository
) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments) {
        val user = companyUserRepository.save(
            CompanyUser(
                username = "user",
                password = passwordEncoder.encode("password"),
                roles = mutableSetOf(Role.COMPANY_USER)
            )
        )

        val coffeeGroup = productGroupRepository.save(
            ProductGroup(
                name = "coffee"
            )
        )
        val espresso = productRepository.save(
            Product(
                name = "espresso",
                price = 3.0.toBigDecimal(),
                group = coffeeGroup
            )
        )
        val cappuccino = productRepository.save(
            Product(
                name = "cappucino",
                price = 5.0.toBigDecimal(),
                group = coffeeGroup
            )
        )
        coffeeGroup.products.addAll(listOf(espresso, cappuccino))

        val croissantGroup = productGroupRepository.save(
            ProductGroup(
                name = "croissants"
            )
        )
        val croissant = productRepository.save(
            Product(
                name = "croissant",
                price = 6.0.toBigDecimal(),
                group = croissantGroup
            )
        )
        val chocolateCroissant = productRepository.save(
            Product(
                name = "chocolate croissant",
                price = 8.0.toBigDecimal(),
                group = croissantGroup
            )
        )
        croissantGroup.products.addAll(listOf(croissant, chocolateCroissant))

        val sandwichGroup = productGroupRepository.save(
            ProductGroup(
                name = "sandwiches"
            )
        )
        val plainSandwich = productRepository.save(
            Product(
                name = "sandwich",
                price = 7.0.toBigDecimal(),
                group = sandwichGroup
            )
        )
        val hamSandwich = productRepository.save(
            Product(
                name = "ham sandwich",
                price = 9.0.toBigDecimal(),
                group = sandwichGroup
            )
        )
        val salamiSandwich = productRepository.save(
            Product(
                name = "salami sandwich",
                price = 9.0.toBigDecimal(),
                group = sandwichGroup
            )
        )
        val premiumSandwich = productRepository.save(
            Product(
                name = "premium sandwich",
                price = 11.0.toBigDecimal(),
                group = sandwichGroup
            )
        )
        sandwichGroup.products.addAll(listOf(plainSandwich, hamSandwich, salamiSandwich, premiumSandwich))

        val freeCoffeeAfterCoffeeOrHamSandwich = couponRepository.save(
            Coupon(
                name = "Free Coffee After Coffees or Sandwiches",
                type = CouponType.POINT,
                eligibleProducts = mutableMapOf(hamSandwich to 1),
                eligibleProductGroups = mutableMapOf(coffeeGroup to 2)
            )
        )
        val freeCoffeeRewardLevel1 = rewardRepository.save(
            Reward(
                threshold = 10.0.toBigDecimal(),
                discountType = DiscountType.PERCENTAGE,
                discount = 100.0.toBigDecimal(),
                products = mutableMapOf(),
                productGroups = mutableMapOf(coffeeGroup to 1),
                coupon = freeCoffeeAfterCoffeeOrHamSandwich
            )
        )
        val freeCoffeeRewardLevel2 = rewardRepository.save(
            Reward(
                threshold = 20.0.toBigDecimal(),
                discountType = DiscountType.PERCENTAGE,
                discount = 100.0.toBigDecimal(),
                products = mutableMapOf(),
                productGroups = mutableMapOf(coffeeGroup to 2),
                coupon = freeCoffeeAfterCoffeeOrHamSandwich
            )
        )
        freeCoffeeAfterCoffeeOrHamSandwich.rewards.addAll(listOf(freeCoffeeRewardLevel1, freeCoffeeRewardLevel2))

        val discountSandwichForChocolateCroissant = couponRepository.save(
            Coupon(
                name = "Discount Sandwich for Chocolate Croissant",
                type = CouponType.PRICE,
                eligibleProducts = mutableMapOf(chocolateCroissant to -1),
                eligibleProductGroups = mutableMapOf()
            )
        )
        val discountSandwichRewardLevel1 = rewardRepository.save(
            Reward(
                threshold = 40.0.toBigDecimal(),
                discountType = DiscountType.FIXED,
                discount = 3.0.toBigDecimal(),
                products = mutableMapOf(),
                productGroups = mutableMapOf(sandwichGroup to 1),
                coupon = discountSandwichForChocolateCroissant
            )
        )
        val discountSandwichRewardLevel2 = rewardRepository.save(
            Reward(
                threshold = 80.0.toBigDecimal(),
                discountType = DiscountType.PERCENTAGE,
                discount = 75.0.toBigDecimal(),
                products = mutableMapOf(premiumSandwich to 2),
                productGroups = mutableMapOf(),
                coupon = discountSandwichForChocolateCroissant
            )
        )
        discountSandwichForChocolateCroissant.rewards.addAll(listOf(discountSandwichRewardLevel1, discountSandwichRewardLevel2))

        val customer1 = customerRepository.save(
            Customer(
                id = 1,
                name = "John Doe",
                username = "johndoe12",
                code = "aa-aa",
                coupons = mutableMapOf()
            )
        )
        val customer2 = customerRepository.save(
            Customer(
                id = 2,
                name = "Billy Smith",
                username = "billysmith98",
                code = "bb-bb",
                coupons = mutableMapOf()
            )
        )
        val customer3 = customerRepository.save(
            Customer(
                id = 3,
                name = "Jane Hoynen",
                username = "jane_h",
                code = "cc-cc",
                coupons = mutableMapOf()
            )
        )
    }
}