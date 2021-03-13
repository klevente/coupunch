package dev.klevente.coupunch.library.exception

class ApiError(
    val status: Int,
    val error: String,
    val message: String
)