package com.example.ordermicroservice.web.controller

import com.example.ordermicroservice.domain.model.AuthException
import com.example.ordermicroservice.domain.model.ResourceNotFoundException
import com.example.ordermicroservice.web.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AppExceptionHandler {
    @ExceptionHandler(AuthException::class)
    fun unauthorized(e: AuthException): ResponseEntity<ErrorDto> {
        return ResponseEntity(ErrorDto(e.message!!), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFound(e: ResourceNotFoundException): ResponseEntity<ErrorDto> {
        return ResponseEntity(ErrorDto(e.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<List<ErrorDto>> {
        val errorList = e.bindingResult.allErrors.map { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            ErrorDto("$fieldName: $errorMessage")
        }
        return ResponseEntity(errorList, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun missingRequestHeader(e: MissingRequestHeaderException): ResponseEntity<List<ErrorDto>> {
        return ResponseEntity(listOf(ErrorDto(e.message)), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun other(e: Exception) {
        e.printStackTrace()
    }
}