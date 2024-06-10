package com.example.authmicroservice.domain.service

import com.example.authmicroservice.data.entity.Person
import com.example.authmicroservice.web.dto.PersonRequestDto

interface PersonService {
    fun create(dto: PersonRequestDto)
    fun getByEmail(email: String): Person
}