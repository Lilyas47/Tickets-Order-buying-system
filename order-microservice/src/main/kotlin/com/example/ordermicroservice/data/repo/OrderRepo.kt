package com.example.ordermicroservice.data.repo

import com.example.ordermicroservice.data.entity.Order
import com.example.ordermicroservice.data.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface OrderRepo : JpaRepository<Order, Int> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET status = :newStatus WHERE id = :id", nativeQuery = true)
    fun updateStatusById(id: Int, newStatus: OrderStatus)
}