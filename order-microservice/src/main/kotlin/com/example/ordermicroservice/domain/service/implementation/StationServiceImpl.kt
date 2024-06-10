package com.example.ordermicroservice.domain.service.implementation

import com.example.ordermicroservice.data.entity.Station
import com.example.ordermicroservice.data.repo.StationRepo
import com.example.ordermicroservice.domain.model.ResourceNotFoundException
import com.example.ordermicroservice.domain.service.StationService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StationServiceImpl(
    private val stationRepo: StationRepo
) : StationService {
    override fun create(station: Station) {
        stationRepo.save(station)
    }

    override fun get(id: Int): Station {
        return stationRepo.findByIdOrNull(id)?: throw ResourceNotFoundException("station not found")
    }
}