package com.example.ordermicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
class OrderMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<OrderMicroserviceApplication>(*args)
}
