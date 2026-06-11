package com.example.mykmpapplication.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykmpapplication.AppColors

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val bgStart = AppColors.gradientStart()
    val bgEnd = AppColors.gradientEnd()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(bgStart, bgEnd)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 40.dp, end = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Red.copy(alpha = 0.2f))
                .clickable { onLogout() }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Log Out",
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Welcome to Home Screen!",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onLogout = {})
}
