package com.example.authmicroservice.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "app.jwt")
data class JwtProperties(
    val secret: String,
    val lifetime: Duration
) {
}