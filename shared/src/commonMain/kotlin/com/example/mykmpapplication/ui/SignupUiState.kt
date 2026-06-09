package com.example.mykmpapplication.ui

import com.example.mykmpapplication.network.NetworkResult

data class SignupUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val signupResult: NetworkResult<String> = NetworkResult.Idle,
    val isLoading: Boolean = false
)
