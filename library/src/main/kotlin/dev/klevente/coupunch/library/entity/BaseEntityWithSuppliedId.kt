package dev.klevente.coupunch.library.entity

import java.io.Serializable
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntityWithSuppliedId(

    @Id
    val id: Long = -1L,

) : Serializable {

    protected val formattedId get() = "id=$id"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as BaseEntity).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "Entity($formattedId)"
}