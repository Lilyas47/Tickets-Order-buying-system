package com.example.authmicroservice.domain.service.implementation

import com.example.authmicroservice.data.repository.PersonRepo
import com.example.authmicroservice.domain.model.ResourceNotFoundException
import com.example.authmicroservice.domain.service.PersonService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.Collections

@Service
class UserDetailsServiceImpl(
    private val personRepo: PersonRepo
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val person = personRepo.findByEmail(username!!)?:throw ResourceNotFoundException("person not found")
        return User(
            person.email,
            person.password,
            Collections.emptyList()
        )
    }
}