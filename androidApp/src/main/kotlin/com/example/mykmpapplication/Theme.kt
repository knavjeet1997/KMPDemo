package com.example.mykmpapplication

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.mykmpapplication.R

@Composable
fun CalculatorTheme(
    content: @Composable () -> Unit
) {
    // Resolve colors dynamically from resource XML (which automatically switches values based on light/dark mode)
    val colorScheme = lightColorScheme(
        primary = colorResource(id = R.color.primary),
        onPrimary = colorResource(id = R.color.on_primary),
        background = colorResource(id = R.color.background),
        surface = colorResource(id = R.color.surface),
        onBackground = colorResource(id = R.color.on_background),
        onSurface = colorResource(id = R.color.on_surface)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
