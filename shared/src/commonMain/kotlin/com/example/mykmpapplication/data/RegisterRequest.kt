package com.example.mykmpapplication.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    @SerialName("c_password") val cPassword: String,
    @SerialName("first_name") val firstName: String = "",
    @SerialName("last_name") val lastName: String = ""
)
