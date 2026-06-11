package com.example.mykmpapplication.ui.login

sealed interface LoginUiEvent {
    data class ShowToast(val message: String, val isSuccess: Boolean) : LoginUiEvent
    data object NavigateToHome : LoginUiEvent
}
