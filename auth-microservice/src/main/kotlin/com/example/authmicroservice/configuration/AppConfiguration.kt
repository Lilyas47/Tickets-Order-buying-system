package com.example.authmicroservice.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties::class)
@OpenAPIDefinition(
    info = Info(title = "Сервис авторизации пользователя", version = "1.0.0"),
    security = [SecurityRequirement(name = "JWT токен")]
)
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    name = "JWT токен")
class AppConfiguration(
    private val userDetailsService: UserDetailsService,
    private val tokenFilter: TokenFilter
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager = configuration.authenticationManager

    @Bean
    fun daoAuthenticationProvider(): AuthenticationProvider = DaoAuthenticationProvider().also {
        it.setPasswordEncoder(passwordEncoder())
        it.setUserDetailsService(userDetailsService)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{ it.disable()}
            .cors { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling { configurer ->
                configurer.authenticationEntryPoint { _, response, _ ->
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.writer.write("{\n" +
                            "  \"message\": \"Unauthorized\"\n" +
                            "}")
                }
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/auth-microservice/v1/auth/register").permitAll()
                    .requestMatchers("/auth-microservice/v1/auth/login").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/**").permitAll()
                    .anyRequest().authenticated()
            }
            .anonymous { it.disable() }
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}