package com.example.authmicroservice.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "sessions")
data class PersonSession(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: Person,
    val token: String,
    val expires: LocalDateTime?
)
