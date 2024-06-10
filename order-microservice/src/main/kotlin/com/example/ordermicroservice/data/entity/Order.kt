package com.example.ordermicroservice.data.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int,
    @Column(name = "user_id")
    val userId: Int,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_station_id", referencedColumnName = "id")
    val stationFrom: Station,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_station_id", referencedColumnName = "id")
    val stationTo: Station,
    @Enumerated(value = EnumType.ORDINAL)
    val status: OrderStatus,
    val created: LocalDateTime
)
