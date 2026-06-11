package com.example.mykmpapplication

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    // Light Mode Palette
    val primaryLight = Color(0xFF1976D2)
    val onPrimaryLight = Color(0xFFFFFFFF)
    val backgroundLight = Color(0xFFF9FBFC)
    val surfaceLight = Color(0xFFFFFFFF)
    val onBackgroundLight = Color(0xFF1A1C1E)
    val onSurfaceLight = Color(0xFF1A1C1E)
    val gradientStartLight = Color(0xFFE3F2FD)
    val gradientEndLight = Color(0xFFF9FBFC)
    
    // Dark Mode Palette
    val primaryDark = Color(0xFF90CAF9)
    val onPrimaryDark = Color(0xFF0D47A1)
    val backgroundDark = Color(0xFF121212)
    val surfaceDark = Color(0xFF1E1E1E)
    val onBackgroundDark = Color(0xFFE3F2FD)
    val onSurfaceDark = Color(0xFFE3F2FD)
    val gradientStartDark = Color(0xFF0D1B2A)
    val gradientEndDark = Color(0xFF121212)
    
    // Calculator/Common Colors
    val calcBgStartLight = Color(0xFFF3F4F6)
    val calcBgEndLight = Color(0xFFE5E7EB)
    val calcDisplayTextLight = Color(0xFF111827)
    val calcExpressionTextLight = Color(0xFF6B7280)
    val calcNumberBgLight = Color(0xFFFFFFFF)
    val calcNumberFgLight = Color(0xFF111827)
    val calcFunctionBgLight = Color(0xFFD1D5DB)
    val calcFunctionFgLight = Color(0xFF111827)
    val calcOperatorBgLight = Color(0xFFF97316)
    val calcOperatorFgLight = Color(0xFFFFFFFF)
    val textFieldContainerLight = Color(0xFFF5F5F5)

    val calcBgStartDark = Color(0xFF111827)
    val calcBgEndDark = Color(0xFF030712)
    val calcDisplayTextDark = Color(0xFFFFFFFF)
    val calcExpressionTextDark = Color(0xFF9CA3AF)
    val calcNumberBgDark = Color(0xFF374151)
    val calcNumberFgDark = Color(0xFFFFFFFF)
    val calcFunctionBgDark = Color(0xFF4B5563)
    val calcFunctionFgDark = Color(0xFFFFFFFF)
    val calcOperatorBgDark = Color(0xFFF97316)
    val calcOperatorFgDark = Color(0xFFFFFFFF)
    val textFieldContainerDark = Color(0xFF252525)

    // Helper to get theme-dependent values easily
    @Composable
    fun gradientStart(darkTheme: Boolean = isSystemInDarkTheme()): Color =
        if (darkTheme) gradientStartDark else gradientStartLight

    @Composable
    fun gradientEnd(darkTheme: Boolean = isSystemInDarkTheme()): Color =
        if (darkTheme) gradientEndDark else gradientEndLight

    @Composable
    fun calcOperatorBg(): Color = calcOperatorBgLight

    @Composable
    fun calcFunctionBg(darkTheme: Boolean = isSystemInDarkTheme()): Color =
        if (darkTheme) calcFunctionBgDark else calcFunctionBgLight
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = AppColors.primaryDark,
            onPrimary = AppColors.onPrimaryDark,
            background = AppColors.backgroundDark,
            surface = AppColors.surfaceDark,
            onBackground = AppColors.onBackgroundDark,
            onSurface = AppColors.onSurfaceDark
        )
    } else {
        lightColorScheme(
            primary = AppColors.primaryLight,
            onPrimary = AppColors.onPrimaryLight,
            background = AppColors.backgroundLight,
            surface = AppColors.surfaceLight,
            onBackground = AppColors.onBackgroundLight,
            onSurface = AppColors.onSurfaceLight
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
