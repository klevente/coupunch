package dev.klevente.coupunch.analytics.checkout

import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<Purchase, Long>