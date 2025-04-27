package org.example.project.domain.error


sealed interface DataError : Error{
    enum class Network : DataError{
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN
    }
}