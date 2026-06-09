package com.example.mykmpapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykmpapplication.data.AuthRepository
import com.example.mykmpapplication.data.AuthRepositoryImpl
import com.example.mykmpapplication.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
class SignupViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SignupUiEvent>()
    val events = _events.asSharedFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue) }
    }

    fun register() {
        val currentState = _uiState.value
        if (currentState.email.isBlank() || currentState.password.isBlank() || currentState.confirmPassword.isBlank()) {
            val errorMsg = "All fields are required"
            _uiState.update { it.copy(signupResult = NetworkResult.Error(errorMsg)) }
            viewModelScope.launch {
                _events.emit(SignupUiEvent.ShowToast(errorMsg, isSuccess = false))
            }
            return
        }

        if (currentState.password != currentState.confirmPassword) {
            val errorMsg = "Passwords do not match"
            _uiState.update { it.copy(signupResult = NetworkResult.Error(errorMsg)) }
            viewModelScope.launch {
                _events.emit(SignupUiEvent.ShowToast(errorMsg, isSuccess = false))
            }
            return
        }

        _uiState.update { it.copy(signupResult = NetworkResult.Loading, isLoading = true) }

        viewModelScope.launch {
            val result = authRepository.register(currentState.email, currentState.password)
            _uiState.update {
                if (result is NetworkResult.Success) {
                    it.copy(
                        email = "",
                        password = "",
                        confirmPassword = "",
                        signupResult = result,
                        isLoading = false
                    )
                } else {
                    it.copy(signupResult = result, isLoading = false)
                }
            }
            if (result is NetworkResult.Success) {
                _events.emit(SignupUiEvent.ShowToast("Registration Successful!", isSuccess = true))
                _events.emit(SignupUiEvent.NavigateToHome)
            } else if (result is NetworkResult.Error) {
                _events.emit(SignupUiEvent.ShowToast(result.message, isSuccess = false))
            }
        }
    }

    fun resetState() {
        _uiState.update { it.copy(signupResult = NetworkResult.Idle) }
    }

    fun observeState(onChange: (SignupUiState) -> Unit) {
        viewModelScope.launch {
            uiState.collect {
                onChange(it)
            }
        }
    }

    fun observeEvents(callback: (SignupUiEvent) -> Unit) {
        viewModelScope.launch {
            events.collect {
                callback(it)
            }
        }
    }
}
