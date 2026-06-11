package com.example.mykmpapplication

import androidx.compose.runtime.*
import com.example.mykmpapplication.data.SessionManager
import com.example.mykmpapplication.ui.home.HomeScreen
import com.example.mykmpapplication.ui.login.LoginScreen
import com.example.mykmpapplication.ui.signup.SignupScreen
import com.example.mykmpapplication.ui.splash.SplashScreen
import org.koin.compose.koinInject

enum class Screen {
    Splash, Login, Signup, Home
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Splash) }
    val sessionManager: SessionManager = koinInject()

    AppTheme {
        when (currentScreen) {
            Screen.Splash -> {
                SplashScreen(
                    onNavigateToHome = { currentScreen = Screen.Home },
                    onNavigateToLogin = { currentScreen = Screen.Login }
                )
            }
            Screen.Login -> {
                LoginScreen(
                    onNavigateToHome = { currentScreen = Screen.Home },
                    onNavigateToSignup = { currentScreen = Screen.Signup }
                )
            }
            Screen.Signup -> {
                SignupScreen(
                    onNavigateToHome = { currentScreen = Screen.Home },
                    onNavigateToLogin = { currentScreen = Screen.Login }
                )
            }
            Screen.Home -> {
                HomeScreen(
                    onLogout = {
                        sessionManager.clear()
                        currentScreen = Screen.Login
                    }
                )
            }
        }
    }
}