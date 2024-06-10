package com.example.authmicroservice.configuration

import com.example.authmicroservice.domain.service.JwtService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(TokenFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token = request.getHeader("Authorization")
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7)
        }

        if (token != null) {
            try {
                val username = jwtService.extractUsername(token)
                if (username != null) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        emptyList()
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
            } catch (e: JwtException) {
                log.error("JWT exception: {}", e.message)
            }
        }

        filterChain.doFilter(request, response)
    }
}