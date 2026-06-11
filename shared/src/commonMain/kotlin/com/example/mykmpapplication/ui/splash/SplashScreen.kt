package com.example.mykmpapplication.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykmpapplication.AppColors
import com.example.mykmpapplication.di.KoinHelper
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = remember { KoinHelper().getSplashViewModel() },
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    LaunchedEffect(viewModel) {
        delay(1500)
        if (viewModel.isUserLoggedIn()) {
            onNavigateToHome()
        } else {
            onNavigateToLogin()
        }
    }

    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0.0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = 800)
        )
    }
    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = 800)
        )
    }

    val bgStart = AppColors.gradientStart()
    val bgEnd = AppColors.gradientEnd()

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(colors = listOf(bgStart, bgEnd))),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "TRACK NINJA",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.calcOperatorBg(),
                        modifier = Modifier
                            .scale(scale.value)
                            .alpha(alpha.value)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Secure & Simple Tracking",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.alpha(alpha.value)
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = AppColors.calcOperatorBg(),
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreenContent()
}
