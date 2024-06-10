package com.example.authmicroservice.data.repository

import com.example.authmicroservice.data.entity.PersonSession
import org.springframework.data.jpa.repository.JpaRepository

interface PersonSessionRepo : JpaRepository<PersonSession, Int> {
}