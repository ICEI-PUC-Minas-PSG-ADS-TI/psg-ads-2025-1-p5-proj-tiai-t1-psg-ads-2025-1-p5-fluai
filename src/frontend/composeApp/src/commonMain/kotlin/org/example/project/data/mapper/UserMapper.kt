package org.example.project.data.mapper

import org.example.project.data.dto.UserRequestDto
import org.example.project.domain.model.User

fun User.toDto(): UserRequestDto {
    return UserRequestDto(
        name = name,
        email = email,
        password = password
    )
}

fun UserRequestDto.toDomain(): User {
    return User(
        name = name,
        email = email,
        password = password
    )
}
