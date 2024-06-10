package com.example.ordermicroservice.domain.service

interface PersonService {
    fun getPersonId(token: String): Int
}