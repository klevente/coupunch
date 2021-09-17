package dev.klevente.coupunch.couponmanager.config

import org.springframework.data.jpa.repository.JpaRepository

interface ConfigRepository : JpaRepository<ConfigEntry, Long> {
    fun findFirstByKey(key: String): ConfigEntry?
}