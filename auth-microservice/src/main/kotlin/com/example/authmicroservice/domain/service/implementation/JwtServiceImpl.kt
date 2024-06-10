package com.example.authmicroservice.domain.service.implementation

import com.example.authmicroservice.configuration.JwtProperties
import com.example.authmicroservice.domain.service.JwtService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.spec.KeySpec
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtServiceImpl(
    private val jwtProperties: JwtProperties
) : JwtService {
    override fun generateToken(userDetails: UserDetails, expiration: LocalDateTime): String {
        val expirationDate = Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant())
        val issuedDate = Date
            .from(expiration.minus(jwtProperties.lifetime)
                .atZone(ZoneId.systemDefault()).toInstant())
        return Jwts.builder()
            .setSubject(userDetails.username)
            .setIssuedAt(issuedDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secret)
            .compact()
    }

    override fun extractUsername(token: String): String {
        return Jwts
            .parser()
            .setSigningKey(jwtProperties.secret)
            .parseClaimsJws(token)
            .body
            .subject
    }
}