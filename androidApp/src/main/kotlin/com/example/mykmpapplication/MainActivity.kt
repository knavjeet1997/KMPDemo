package com.example.mykmpapplication

import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mykmpapplication.ui.SplashScreen
import com.example.mykmpapplication.ui.LoginScreen
import com.example.mykmpapplication.ui.SignupScreen
import com.example.mykmpapplication.ui.HomeScreen
import com.example.mykmpapplication.data.SessionManager
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val sessionManager: SessionManager = koinInject()
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(
                        onNavigateToHome = {
                            navController.navigate("home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate("login") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    )
                }
                composable("login") {
                    LoginScreen(
                        onNavigateToHome = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onNavigateToSignup = {
                            navController.navigate("signup")
                        }
                    )
                }
                composable("signup") {
                    SignupScreen(
                        onNavigateToHome = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate("login") {
                                popUpTo("signup") { inclusive = true }
                            }
                        }
                    )
                }
                composable("home") {
                    HomeScreen(
                        onLogout = {
                            sessionManager.clear()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    )
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