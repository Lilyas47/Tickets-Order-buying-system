package com.example.authmicroservice.domain.service.implementation

import com.example.authmicroservice.configuration.JwtProperties
import com.example.authmicroservice.data.entity.PersonSession
import com.example.authmicroservice.data.repository.PersonSessionRepo
import com.example.authmicroservice.domain.service.AuthService
import com.example.authmicroservice.domain.service.JwtService
import com.example.authmicroservice.domain.service.PersonService
import com.example.authmicroservice.web.dto.AuthRequestDto
import com.example.authmicroservice.web.dto.AuthResponseDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val personService: PersonService,
    private val userDetailsService: UserDetailsService,
    private val personSessionRepo: PersonSessionRepo,
    private val jwtProperties: JwtProperties
) : AuthService {
    override fun login(request: AuthRequestDto): AuthResponseDto {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val userDetails = userDetailsService.loadUserByUsername(request.email)
        val person = personService.getByEmail(request.email)
        val expiredDate = LocalDateTime.now().plus(jwtProperties.lifetime)
        val token = jwtService.generateToken(userDetails, expiredDate)
        val personSession = PersonSession(
            0,
            person,
            token,
            expiredDate
        )
        personSessionRepo.save(personSession)

        return AuthResponseDto(token)
    }
}