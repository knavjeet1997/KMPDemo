package com.example.mykmpapplication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykmpapplication.common.CommonFlow
import com.example.mykmpapplication.common.CommonStateFlow
import com.example.mykmpapplication.common.asCommonFlow
import com.example.mykmpapplication.common.asCommonStateFlow
import com.example.mykmpapplication.data.AuthRepository
import com.example.mykmpapplication.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: CommonStateFlow<LoginUiState> = _uiState.asStateFlow().asCommonStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>(extraBufferCapacity = 64)
    val events: CommonFlow<LoginUiEvent> = _events.asSharedFlow().asCommonFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    private fun sendEvent(event: LoginUiEvent) {
        _events.tryEmit(event)
    }

    fun login() {
        val currentState = _uiState.value
        
        val validationError = when {
            currentState.email.isBlank() || currentState.password.isBlank() -> "All fields are required"
            else -> null
        }

        if (validationError != null) {
            sendEvent(LoginUiEvent.ShowToast(validationError, isSuccess = false))
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = authRepository.login(currentState.email, currentState.password)
            when (result) {
                is NetworkResult.Success -> {
                    _uiState.value = LoginUiState()
                    sendEvent(LoginUiEvent.ShowToast("Login Successful!", isSuccess = true))
                    sendEvent(LoginUiEvent.NavigateToHome)
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(LoginUiEvent.ShowToast(result.message, isSuccess = false))
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
