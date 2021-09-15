package dev.klevente.coupunch.library.exception

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

abstract class RestException(message: String): RuntimeException(message) {
    abstract fun toApiError(): ApiError
}

class EntityNotFoundException(message: String) : RestException(message) {
    constructor(clazz: KClass<*>, propertyName: String, propertyValue: Any) : this("${clazz.simpleName!!} not found " +
            "($propertyName=$propertyValue)")

    override fun toApiError() = ApiError(
        status = HttpStatus.NOT_FOUND.value(),
        error = "NOT_FOUND",
        message = localizedMessage
    )

    companion object {
        fun <T: Any> byId(clazz: KClass<*>, id: T) = EntityNotFoundException(clazz, "id", id)
    }
}

class BadRequestException(message: String) : RestException(message) {
    override fun toApiError() = ApiError(
        status = HttpStatus.BAD_REQUEST.value(),
        error = "BAD_REQUEST",
        message = localizedMessage
    )
}