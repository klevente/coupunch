package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.library.entity.BaseEntity
import javax.persistence.*

enum class ConfigType {
    STRING, INT, DOUBLE
}

@Entity
@Table(name = "app_config_entry")
class ConfigEntry(
    id: Long = -1L,

    @Column(nullable = false)
    val key: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: ConfigType = ConfigType.STRING,

    @Column(nullable = false)
    val value: String = ""
) : BaseEntity(id) {
    override fun toString() = "ConfigEntry($formattedId, type=$type, value=$value"

    companion object {
        fun <T> create(key: String, value: T): ConfigEntry {
            val v = value.toString()
            return when (value) {
                is String -> ConfigEntry(key = key, type = ConfigType.STRING, value = v)
                is Int -> ConfigEntry(key = key, type = ConfigType.INT, value = v)
                is Double -> ConfigEntry(key = key, type = ConfigType.DOUBLE, value = v)
                else -> throw IllegalArgumentException("Unknown config value type")
            }
        }
    }

    fun string() = value
    fun int() = value.toInt()
    fun double() = value.toDouble()
}