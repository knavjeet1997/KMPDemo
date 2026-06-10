package com.example.mykmpapplication.ui

sealed interface LoginUiEvent {
    data class ShowToast(val message: String, val isSuccess: Boolean) : LoginUiEvent
    data object NavigateToHome : LoginUiEvent
}
