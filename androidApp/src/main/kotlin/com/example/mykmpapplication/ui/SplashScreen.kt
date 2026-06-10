package com.example.mykmpapplication.ui

import android.content.res.Configuration
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykmpapplication.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
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

    LaunchedEffect(viewModel) {
        delay(1500)
        if (viewModel.isUserLoggedIn()) {
            onNavigateToHome()
        } else {
            onNavigateToLogin()
        }
    }

    val bgStart = colorResource(id = R.color.gradient_start)
    val bgEnd = colorResource(id = R.color.gradient_end)

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
                        color = colorResource(id = R.color.calc_operator_bg),
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
                        color = colorResource(id = R.color.calc_operator_bg),
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun SplashScreenPreviewLight() {
    SplashScreen(onNavigateToHome = {}, onNavigateToLogin = {})
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SplashScreenPreviewDark() {
    SplashScreen(onNavigateToHome = {}, onNavigateToLogin = {})
}
