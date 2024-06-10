package com.example.ordermicroservice.web.controller

import com.example.ordermicroservice.data.entity.Station
import com.example.ordermicroservice.domain.service.OrderService
import com.example.ordermicroservice.domain.service.UpdateService
import com.example.ordermicroservice.web.dto.ErrorDto
import com.example.ordermicroservice.web.dto.OrderRequestDto
import com.example.ordermicroservice.web.dto.OrderResponseDto
import com.example.ordermicroservice.web.dto.StationResponseDto
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
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
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order-microservice/v1/order")
@Validated
@Tag(name = "Order Controller", description = "Управляет заказами")
class OrderController(
    private val orderService: OrderService,
    private val updateService: UpdateService
) {
    companion object {
        fun mapEntityToDto(station: Station): StationResponseDto = StationResponseDto(station.id, station.name)
    }

    @Operation(
        summary = "Создание заказа",
        description = "Создает новый заказ"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Request completed successfully", content = [Content()]),
            ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = ArraySchema(schema = Schema(implementation = ErrorDto::class))
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "User is not authorized",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ErrorDto::class)
                )]
            ),
            ApiResponse(responseCode = "500", description = "Server-side error", content = [Content()])
        ]
    )
    @PostMapping
    fun create(
        @Parameter(hidden = true)
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody request: OrderRequestDto
    ): ResponseEntity<*> {
        val order = orderService.create(request, token)
        updateService.add(order)
        return ResponseEntity(null, HttpStatus.CREATED)
    }

    @Operation(summary = "Получение заказа по ID", description = "Возвращает заказ по ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Request completed successfully",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = OrderResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = ArraySchema(schema = Schema(implementation = ErrorDto::class))
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Order not found",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ErrorDto::class)
                )]
            ),
            ApiResponse(responseCode = "500", description = "Server-side error", content = [Content()])
        ]
    )
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): OrderResponseDto {
        val order = orderService.get(id)
        return OrderResponseDto(
            order.id,
            order.userId,
            mapEntityToDto(order.stationFrom),
            mapEntityToDto(order.stationTo),
            order.status,
            order.created
        )
    }
}
