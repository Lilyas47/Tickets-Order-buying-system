package com.example.ordermicroservice.web.dto

import com.example.ordermicroservice.data.entity.OrderStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class OrderRequestDto(
    @Schema(
        description = "ID точки отправления",
        required = true,
        example = "1"
    )
    @field:NotNull(message = "ID точки отправления не должен быть пустым")
    val fromStationId: Int,

    @Schema(
        description = "ID точки прибытия",
        required = true,
        example = "2"
    )
    @field:NotNull(message = "ID точки отправления не должно быть пустым")
    val toStationId: Int
)

data class OrderResponseDto(
    @Schema(
        description = "ID заказа",
        example = "1",
        required = true
    )
    val id: Int,

    @Schema(
        description = "ID заказчика",
        example = "1",
        required = true
    )
    val userId: Int,

    @Schema(
        description = "Точка отправления",
        required = true
    )
    val from: StationResponseDto,

    @Schema(
        description = "Точка прибытия",
        required = true
    )
    val to: StationResponseDto,

    @Schema(
        description = "Статус заказа",
        required = true,
        example = "CHECK"
    )
    val status: OrderStatus,

    @Schema(
        description = "Дата и время создания заказа",
        required = true,
        example = "2024-06-09T09:27:36.052051"
    )
    val created: LocalDateTime
)

