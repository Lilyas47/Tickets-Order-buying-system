package com.example.ordermicroservice.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "stations")
data class Station(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @Column(name = "station")
    val name: String
)
