package org.example.project.data.mapper

import org.example.project.data.database.entities.UserEntity
import org.example.project.data.dto.UserRequestDto
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.User

fun User.toDto(): UserRequestDto {
    return UserRequestDto(
        username = username,
        password = password,
        email = email
    )
}

fun UserRequestDto.toDomain(): User {
    return User(
        username = username,
        password = password,
        email = email
    )
}

fun UserEntity.toAuthData(): AuthData{
    return AuthData(
        email = email,
        uuid = uid,
        username = username,
        token = authToken,
        isLogged = isLogged
    )
}

