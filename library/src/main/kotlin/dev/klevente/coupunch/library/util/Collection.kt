package dev.klevente.coupunch.library.util

inline fun <T, reified R> Collection<T>.mapToArray(transform: (T) -> R): Array<R> = map(transform).toTypedArray()

inline fun <K, V, reified R> Map<K, V>.mapToArray(transform: (Map.Entry<K, V>) -> R): Array<R> = map(transform).toTypedArray()

fun <K, V> Iterable<Pair<K, V>>.toMutableMap() = toMap(mutableMapOf())