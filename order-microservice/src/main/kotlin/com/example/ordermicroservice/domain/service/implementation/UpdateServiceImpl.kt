package com.example.ordermicroservice.domain.service.implementation

import com.example.ordermicroservice.data.entity.Order
import com.example.ordermicroservice.data.entity.OrderStatus
import com.example.ordermicroservice.domain.service.OrderService
import com.example.ordermicroservice.domain.service.UpdateService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.BlockingQueue

@Service
class UpdateServiceImpl(
    private val orderService: OrderService,
    private val blockingQueue: BlockingQueue<Int>
) : UpdateService {
    private val logger = LoggerFactory.getLogger(UpdateServiceImpl::class.java)

    override fun add(order: Order) {
        while (!blockingQueue.add(order.id)){}
        logger.info("Order added in queue successfully")
    }

    @Scheduled(fixedRate = 5_000)
    fun handleOrder() {
        if (blockingQueue.isEmpty()) return

        try {
            val orderId = blockingQueue.take()
            val random = getRandom()
            if (random % 3 == 0L) {
                orderService.updateStatus(orderId, OrderStatus.REJECTION)
                logger.info("New order status : {}", OrderStatus.REJECTION)
            } else {
                orderService.updateStatus(orderId, OrderStatus.SUCCESS)
                logger.info("New order status : {}", OrderStatus.SUCCESS)
            }
        } catch (e: Exception) {
            logger.error(e.message)
        }

    }

    private fun getRandom(): Long = (System.currentTimeMillis() % 100)
}