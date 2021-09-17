package dev.klevente.coupunch.analytics.checkout

import dev.klevente.coupunch.library.entity.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "purchases")
class Purchase(
    id: Long = -1L,

    @Column(nullable = false)
    val time: LocalDateTime = LocalDateTime.MIN,

    @Column(nullable = false)
    val userId: Long = -1L,

    @Column(nullable = false)
    val product: String = "",

    @Column(nullable = false)
    val amount: Int = -1,

    @Column(nullable = false)
    val price: Double = -1.0
) : BaseEntity(id)