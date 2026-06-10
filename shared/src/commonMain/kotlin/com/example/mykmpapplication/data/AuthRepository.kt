package com.example.mykmpapplication.data

import com.example.mykmpapplication.network.NetworkResult

interface AuthRepository {
    suspend fun register(email: String, password: String): NetworkResult<String>
    suspend fun login(email: String, password: String): NetworkResult<String>
}
