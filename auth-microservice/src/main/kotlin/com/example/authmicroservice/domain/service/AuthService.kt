package com.example.authmicroservice.domain.service

import com.example.authmicroservice.web.dto.AuthRequestDto
import com.example.authmicroservice.web.dto.AuthResponseDto

interface AuthService {
    fun login(request: AuthRequestDto): AuthResponseDto
}