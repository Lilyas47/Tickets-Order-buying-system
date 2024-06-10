package com.example.ordermicroservice.domain.service.implementation

import com.example.ordermicroservice.configuration.AuthProperties
import com.example.ordermicroservice.domain.model.AuthException
import com.example.ordermicroservice.domain.service.PersonService
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service

@Service
class PersonServiceImpl(
    private val authProperties: AuthProperties,
    private val okHttpClient: OkHttpClient
) : PersonService {
    override fun getPersonId(token: String): Int {
        val request = Request.Builder()
            .url(authProperties.address)
            .header("Authorization", token)
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            return JsonParser.parseString(responseBody).asJsonObject.get("id").asInt
        } else {
            throw AuthException("Unauthorized")
        }
    }
}