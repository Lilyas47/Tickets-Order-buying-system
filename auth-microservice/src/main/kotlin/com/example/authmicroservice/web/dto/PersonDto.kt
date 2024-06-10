package com.example.authmicroservice.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class PersonRequestDto(
    @field:NotNull(message = "Имя пользователя не должно быть пустым")
    @field:Schema(
        description = "Имя пользователя",
        example = "some_nickname",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val nickname: String,

    @field:NotNull(message = "Email пользователя не должен быть пустым")
    @field:Email(message = "Email пользователя должен быть валидным")
    @field:Schema(
        description = "Email пользователя",
        example = "some_email@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val email: String,

    @field:NotNull(message = "Пароль не должен быть пустым")
    @field:Size(min = 8, message = "Размер пароля должен быть не меньше 8 символов")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$",
        message = "Пароль должен содержать только цифры, буквы и спец символы"
    )
    @field:Schema(
        description = "Пароль",
        example = "some_password",
        minLength = 8,
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.WRITE_ONLY
    )
    val password: String
)

@Schema
data class PersonResponseDto(
    @Schema(
        description = "ID пользователя в БД",
        example = "1",
        required = true
    )
    val id: Int? = null,

    @Schema(
        description = "Имя пользователя",
        example = "some_nickname",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val nickname: String? = null,

    @Schema(
        description = "Email пользователя",
        example = "some_email@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:Email
    val email: String? = null,

    @Schema(
        description = "Пароль",
        accessMode = Schema.AccessMode.WRITE_ONLY,
        example = "some_password",
        minLength = 8,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$")
    val password: String? = null,

    @Schema(
        description = "Дата и время регистрации пользователя",
        example = "2024-06-09T09:27:36.052051"
    )
    val created: LocalDateTime? = null
)
