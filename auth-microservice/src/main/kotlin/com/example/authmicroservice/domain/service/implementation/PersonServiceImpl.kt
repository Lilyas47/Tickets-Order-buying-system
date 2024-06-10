package com.example.authmicroservice.domain.service.implementation

import com.example.authmicroservice.data.entity.Person
import com.example.authmicroservice.data.repository.PersonRepo
import com.example.authmicroservice.domain.model.ResourceNotFoundException
import com.example.authmicroservice.domain.service.PersonService
import com.example.authmicroservice.web.dto.PersonRequestDto
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class PersonServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val personRepo: PersonRepo
) : PersonService {
    @Transactional
    override fun create(dto: PersonRequestDto) {
        if (personRepo.findByEmail(dto.email) != null) {
            throw IllegalStateException("person already exists")
        }
        val encodedPassword = passwordEncoder.encode(dto.password)
        personRepo.save(
            Person(
                0,
                dto.nickname,
                dto.email,
                encodedPassword,
                LocalDateTime.now()
            )
        )
    }

    override fun getByEmail(email: String): Person {
        return personRepo.findByEmail(email) ?: throw ResourceNotFoundException("person not found")
    }
}