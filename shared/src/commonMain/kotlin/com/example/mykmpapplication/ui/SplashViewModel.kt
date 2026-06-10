package com.example.mykmpapplication.ui

import androidx.lifecycle.ViewModel
import com.example.mykmpapplication.data.SessionManager

class SplashViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }
}
