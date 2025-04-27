package org.example.project.domain.error

enum class NameError : Error{
    TOO_SHORT,
    NO_UPPERCASE
}

enum class PasswordError : Error{
    TOO_SHORT
}

enum class EmailError : Error{
    INVALID_EMAIL
}