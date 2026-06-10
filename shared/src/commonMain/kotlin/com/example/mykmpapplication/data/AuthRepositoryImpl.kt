package com.example.mykmpapplication.data

import com.example.mykmpapplication.network.ApiClient
import com.example.mykmpapplication.network.ApiEndpoints
import com.example.mykmpapplication.network.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AuthRepositoryImpl(
    private val client: HttpClient = ApiClient.client,
    private val sessionManager: SessionManager = SessionManager()
) : AuthRepository {

    private fun extractToken(responseBody: String): String {
        return try {
            val json = Json { ignoreUnknownKeys = true }
            val jsonObject = json.parseToJsonElement(responseBody).jsonObject
            val data = jsonObject["data"]?.jsonObject
            val token = data?.get("token")?.jsonPrimitive?.content
                ?: data?.get("user_id")?.jsonPrimitive?.content
                ?: jsonObject["token"]?.jsonPrimitive?.content
                ?: "dummy_token_123"
            token
        } catch (e: Exception) {
            "dummy_token_123"
        }
    }

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
                val token = extractToken(responseBody)
                sessionManager.saveToken(token)
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Failed with code ${response.status.value}: $responseBody")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Network failure: ${e.message}", e)
        }
    }

    override suspend fun login(email: String, password: String): NetworkResult<String> {
        val requestBody = LoginRequest(
            email = email,
            password = password,
            check = ""
        )
        return try {
            val response = client.post(ApiEndpoints.LOGIN) {
                setBody(requestBody)
            }

            val responseBody = response.bodyAsText()
            if (response.status.isSuccess()) {
                val token = extractToken(responseBody)
                sessionManager.saveToken(token)
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Failed with code ${response.status.value}: $responseBody")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Network failure: ${e.message}", e)
        }
    }
}
