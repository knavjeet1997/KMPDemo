package com.example.mykmpapplication.data

import com.example.mykmpapplication.network.ApiClient
import com.example.mykmpapplication.network.ApiEndpoints
import com.example.mykmpapplication.network.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class AuthRepositoryImpl(
    private val client: HttpClient = ApiClient.client
) : AuthRepository {
    override suspend fun register(email: String, password: String): NetworkResult<String> {
        val requestBody = RegisterRequest(
            email = email,
            password = password,
            cPassword = password
        )
        return try {
            val response = client.post(ApiEndpoints.REGISTER) {
                setBody(requestBody)
            }

            val responseBody = response.bodyAsText()
            if (response.status.isSuccess()) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Failed with code ${response.status.value}: $responseBody")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Network failure: ${e.message}", e)
        }
    }
}
