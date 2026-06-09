package com.example.mykmpapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.mykmpapplication.R

@Composable
fun HomeScreen() {
    val bgStart = colorResource(id = R.color.gradient_start)
    val bgEnd = colorResource(id = R.color.gradient_end)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(bgStart, bgEnd)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to Home Screen!",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
