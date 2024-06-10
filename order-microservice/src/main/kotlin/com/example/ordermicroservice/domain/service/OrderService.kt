package com.example.ordermicroservice.domain.service

import com.example.ordermicroservice.data.entity.Order
import com.example.ordermicroservice.data.entity.OrderStatus
import com.example.ordermicroservice.web.dto.OrderRequestDto

interface OrderService {
    fun create(request: OrderRequestDto, token: String): Order

    fun updateStatus(id: Int, status: OrderStatus)

    fun get(id: Int): Order
}