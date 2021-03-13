package dev.klevente.coupunch.library.exception

import kotlin.reflect.KClass

class EntityNotFoundException(message: String) : RuntimeException(message) {
    constructor(clazz: KClass<*>, id: Long) : this("${clazz.simpleName!!} not found (id=$id)")

    constructor(clazz: KClass<*>, name: String) : this("${clazz.simpleName!!} not found (name=$name)")
}

class BadRequestException(message: String) : RuntimeException(message)