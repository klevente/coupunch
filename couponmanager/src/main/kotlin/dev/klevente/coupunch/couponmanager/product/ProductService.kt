package dev.klevente.coupunch.couponmanager.product

interface ProductService {
    fun getProduct(id: Long): Product
}