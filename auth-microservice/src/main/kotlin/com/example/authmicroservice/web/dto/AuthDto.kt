package com.example.authmicroservice.web.dto

import io.swagger.v3.oas.annotations.media.Schema


import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AuthRequestDto(
    @field:Schema(
        description = "Электронная почта пользователя",
        example = "some_email@gmail.com",
        required = true
    )
    @field:NotNull(message = "Электронная почта пользователя не должна быть пустой")
    @field:Email(message = "Электронная почта пользователя должна быть валидной")
    val email: String,

    @field:Schema(
        description = "Пароль",
        example = "some_password",
        minLength = 8,
        required = true
    )
    @field:NotNull(message = "Пароль не должен быть пустым")
    @field:Size(min = 8, message = "Длина пароля должна быть не меньше 8")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$",
        message = "Пароль должен содержать только цифры, буквы и спец символы"
    )
    val password: String
)


data class AuthResponseDto(
    @field:Schema(
        description = "Сгенерированный JWT токен",
        example = "am52cm52Z25pYWVyamlndmppZmp0bmh1YXJqaW92bnVlZml2bnJzZXViYWlmaml0",
        required = true
    )
    val token: String
)