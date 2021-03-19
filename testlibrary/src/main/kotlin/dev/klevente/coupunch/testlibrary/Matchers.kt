package dev.klevente.coupunch.testlibrary

import org.mockito.Mockito

inline fun <reified T> any() : T {
    Mockito.any<T>()
    return uninitialized()
}

@Suppress("UNCHECKED_CAST")
fun <T> uninitialized(): T = null as T