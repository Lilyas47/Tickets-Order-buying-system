package com.example.authmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<AuthMicroserviceApplication>(*args)
}
