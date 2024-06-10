package com.example.ordermicroservice.data.repo

import com.example.ordermicroservice.data.entity.Station
import org.springframework.data.jpa.repository.JpaRepository

interface StationRepo : JpaRepository<Station, Int> {
}