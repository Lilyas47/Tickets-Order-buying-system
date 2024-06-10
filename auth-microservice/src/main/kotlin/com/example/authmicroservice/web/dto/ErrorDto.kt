package com.example.authmicroservice.web.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ErrorDto(
    @Schema(
        description = "Сообщение ошибки",
        example = "Сообщение об ошибке",
        required = true
    )
    val message: String
) {
}