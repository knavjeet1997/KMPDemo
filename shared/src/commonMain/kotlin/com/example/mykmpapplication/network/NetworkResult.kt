package com.example.mykmpapplication.network

sealed interface NetworkResult<out T> {
    data object Idle : NetworkResult<Nothing>
    data object Loading : NetworkResult<Nothing>
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class Error(val message: String, val exception: Throwable? = null) : NetworkResult<Nothing>
}
