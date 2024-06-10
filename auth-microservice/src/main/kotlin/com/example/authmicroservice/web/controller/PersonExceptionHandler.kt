package com.example.authmicroservice.web.controller

import com.example.authmicroservice.domain.model.ResourceNotFoundException
import com.example.authmicroservice.web.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PersonExceptionHandler {
    @ExceptionHandler(IllegalStateException::class)
    fun illegalState(e: IllegalStateException): ResponseEntity<ErrorDto> {
        return ResponseEntity(ErrorDto(e.message!!), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFound(e: ResourceNotFoundException): ResponseEntity<ErrorDto> {
        return ResponseEntity(ErrorDto(e.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun authentication(e: AuthenticationException): ResponseEntity<ErrorDto> {
        return ResponseEntity(ErrorDto(e.message!!), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<List<ErrorDto>> {
        val errorList = mutableListOf<ErrorDto>()
        e.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errorList.add(ErrorDto("$fieldName: $errorMessage"))
        }
        return ResponseEntity(errorList, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun missingRequestHeader(e: MissingRequestHeaderException): ResponseEntity<List<ErrorDto>> {
        return ResponseEntity(listOf(ErrorDto(e.message)), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<List<ErrorDto>> {
        return ResponseEntity(listOf(ErrorDto(e.message!!)), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun other(e: Exception) {
        e.printStackTrace()
    }
}