package com.example.mykmpapplication

import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mykmpapplication.ui.LoginScreen
import com.example.mykmpapplication.ui.SignupScreen
import com.example.mykmpapplication.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            var currentScreen by remember { mutableStateOf("login") }

            Crossfade(targetState = currentScreen, label = "screenTransition") { screen ->
                when (screen) {
                    "login" -> LoginScreen(
                        onNavigateToHome = {
                            currentScreen = "home"
                        },
                        onNavigateToSignup = {
                            currentScreen = "signup"
                        }
                    )
                    "signup" -> SignupScreen(
                        onNavigateToHome = {
                            currentScreen = "home"
                        },
                        onNavigateToLogin = {
                            currentScreen = "login"
                        }
                    )
                    "home" -> HomeScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}