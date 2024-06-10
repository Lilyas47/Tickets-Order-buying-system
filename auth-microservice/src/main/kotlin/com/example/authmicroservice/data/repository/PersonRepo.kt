package com.example.authmicroservice.data.repository

import com.example.authmicroservice.data.entity.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepo : JpaRepository<Person, Int>{
    fun findByEmail(email: String): Person?
}