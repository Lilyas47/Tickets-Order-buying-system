package com.example.authmicroservice.web.controller


import com.example.authmicroservice.domain.service.AuthService
import com.example.authmicroservice.domain.service.PersonService
import com.example.authmicroservice.web.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth-microservice/v1/auth")
@Tag(name = "Person Controller", description = "Управляет пользователями")
@Validated
class PersonController(
    private val personService: PersonService,
    private val authService: AuthService
) {

    @Operation(
        summary = "Регистрация пользователя",
        description = "Создает нового пользователя"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "User successfully registered",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorDto::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Server-side error",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorDto::class))]
            )
        ]
    )
    @PostMapping("/register")
    fun register(@Valid @RequestBody dto: PersonRequestDto): ResponseEntity<*> {
        personService.create(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    @Operation(summary = "Вход пользователя", description = "Возвращает JWT токен аутентифицированному пользователю")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Request completed successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = AuthResponseDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = [Content(mediaType = "application/json", array = ArraySchema(
                    schema = Schema(implementation = AuthResponseDto::class)
                ))]
            ),
            ApiResponse(
                responseCode = "401",
                description = "User unauthorized",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorDto::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [Content()]
            )
        ]
    )
    @PostMapping("/login")
    fun login(@Valid @RequestBody auth: AuthRequestDto): AuthResponseDto {
        return authService.login(auth)
    }

    @Operation(summary = "Получение информации о пользователе", description = "Возвращает данные об аутентифицированном пользователе")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Request completed successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = PersonResponseDto::class))]
            ),
            ApiResponse(
                responseCode = "401",
                description = "User unauthorized",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Server-side error",
                content = [Content()]
            )
        ]
    )
    @GetMapping
    fun get(): PersonResponseDto {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = personService.getByEmail(email)
        return PersonResponseDto(user.id, user.nickname, user.email, user.password, user.created)
    }
}
