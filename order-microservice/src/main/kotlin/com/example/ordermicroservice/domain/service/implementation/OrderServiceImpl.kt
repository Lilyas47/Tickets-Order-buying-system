package com.example.ordermicroservice.domain.service.implementation

import com.example.ordermicroservice.data.entity.Order
import com.example.ordermicroservice.data.entity.OrderStatus
import com.example.ordermicroservice.data.repo.OrderRepo
import com.example.ordermicroservice.domain.model.ResourceNotFoundException
import com.example.ordermicroservice.domain.service.OrderService
import com.example.ordermicroservice.domain.service.PersonService
import com.example.ordermicroservice.domain.service.StationService
import com.example.ordermicroservice.web.dto.OrderRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderServiceImpl(
    private val personService: PersonService,
    private val stationService: StationService,
    private val orderRepo: OrderRepo
) : OrderService {
    @Transactional
    override fun create(request: OrderRequestDto, token: String): Order {
        val personId = personService.getPersonId(token)
        val stationFrom = stationService.get(request.fromStationId)
        val stationTo = stationService.get(request.toStationId)
        val order = Order(
            0,
            personId,
            stationFrom,
            stationTo,
            OrderStatus.CHECK,
            LocalDateTime.now()
        )
        return orderRepo.save(order)
    }

    @Transactional
    override fun updateStatus(id: Int, status: OrderStatus) {
        orderRepo.updateStatusById(id, status)
    }

    override fun get(id: Int): Order {
        return orderRepo.findByIdOrNull(id)?: throw ResourceNotFoundException("order not found")
    }

}