package com.example.ordermicroservice.web.dto


import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class StationRequestDto(
    @Schema(
        description = "Название станции",
        required = true,
        example = "station name"
    )
    @field:NotNull(message = "Название станции не должно быть пустым")
    @field:NotBlank(message = "Название станции не должно быть пустым")
    val name: String
)

data class StationResponseDto(
    @Schema(
        description = "ID станции",
        example = "1",
        required = true
    )
    val id: Int,

    @Schema(
        description = "Название станции",
        example = "Station name",
        required = true
    )
    val stationName: String
)
