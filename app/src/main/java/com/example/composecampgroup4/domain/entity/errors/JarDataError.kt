package com.example.composecampgroup4.domain.entity.errors

sealed interface JarDataError : Error {
    enum class Network : JarDataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }
    data object WrongId : JarDataError
    data class Unknown(val message: String) : JarDataError
}