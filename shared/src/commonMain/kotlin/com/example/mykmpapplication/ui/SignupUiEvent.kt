package com.example.mykmpapplication.ui

sealed interface SignupUiEvent {
    data class ShowToast(val message: String, val isSuccess: Boolean) : SignupUiEvent
    data object NavigateToHome : SignupUiEvent
}
