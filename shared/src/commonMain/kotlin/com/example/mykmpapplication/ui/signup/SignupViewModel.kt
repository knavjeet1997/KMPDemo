package com.example.mykmpapplication.ui.signup

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

class SignupViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: CommonStateFlow<SignupUiState> = _uiState.asStateFlow().asCommonStateFlow()

    private val _events = MutableSharedFlow<SignupUiEvent>(extraBufferCapacity = 64)
    val events: CommonFlow<SignupUiEvent> = _events.asSharedFlow().asCommonFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue) }
    }

    private fun sendEvent(event: SignupUiEvent) {
        _events.tryEmit(event)
    }

    fun register() {
        val currentState = _uiState.value
        
        val validationError = when {
            currentState.email.isBlank() || currentState.password.isBlank() || currentState.confirmPassword.isBlank() -> "All fields are required"
            currentState.password != currentState.confirmPassword -> "Passwords do not match"
            else -> null
        }

        if (validationError != null) {
            sendEvent(SignupUiEvent.ShowToast(validationError, isSuccess = false))
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = authRepository.register(currentState.email, currentState.password)
            when (result) {
                is NetworkResult.Success -> {
                    _uiState.value = SignupUiState()
                    sendEvent(SignupUiEvent.ShowToast("Registration Successful!", isSuccess = true))
                    sendEvent(SignupUiEvent.NavigateToHome)
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    sendEvent(SignupUiEvent.ShowToast(result.message, isSuccess = false))
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
