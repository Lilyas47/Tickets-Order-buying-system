package com.example.ordermicroservice.configuration

import com.google.gson.Gson
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

@Configuration
@EnableConfigurationProperties(AuthProperties::class)
@OpenAPIDefinition(
    info = Info(title = "Сервис продажи билетов", version = "1.0.0"),
    security = [SecurityRequirement(name = "JWT токен")]
)
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    name = "JWT токен")
class AppConfiguration {

    @Bean
    fun okHttpClient(): OkHttpClient = OkHttpClient()

    @Bean
    fun blockingQueue(): BlockingQueue<Int> = ArrayBlockingQueue(5)
}