package com.example.ordermicroservice.web.controller

import com.example.ordermicroservice.data.entity.Station
import com.example.ordermicroservice.domain.service.StationService
import com.example.ordermicroservice.web.dto.ErrorDto
import com.example.ordermicroservice.web.dto.StationRequestDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order-microservice/v1/station")
@Tag(name = "Station Controller", description = "Управляет станциями")
@Validated
class StationController(
    private val stationService: StationService
) {
    companion object {
        fun mapDtoToEntity(request: StationRequestDto): Station = Station(0, request.name)
    }

    @Operation(summary = "Создание станции", description = "Добавляет запись о новой станции в БД")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Request completed successfully", content = arrayOf(Content())),
        ApiResponse(responseCode = "400", description = "Invalid input data", content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = ArraySchema(schema = Schema(implementation = ErrorDto::class)))]),
        ApiResponse(responseCode = "500", description = "Server-side error", content = [Content()])
    ])
    @PostMapping
    fun create(@Valid @RequestBody request: StationRequestDto): ResponseEntity<*> {
        stationService.create(mapDtoToEntity(request))
        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }
}