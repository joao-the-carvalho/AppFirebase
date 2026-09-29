package com.example.appfirebase.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary        = PcbGreenLight,
    onPrimary      = Color.White,
    secondary      = CopperGold,
    onSecondary    = Color.Black,
    background     = CarbonBlack,
    surface        = SurfaceDark,
    onBackground   = TextPrimary,
    onSurface      = TextPrimary,
    error          = ErrorRed
)

@Composable
fun AppFirebaseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}