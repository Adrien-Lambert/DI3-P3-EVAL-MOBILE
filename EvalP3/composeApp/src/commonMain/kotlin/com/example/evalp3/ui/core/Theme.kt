package com.example.evalp3.ui.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PortalGreen = Color(0xFF97CE4C)
private val PortalGreenDark = Color(0xFF6B9E23)
private val DarkBackground = Color(0xFF1A1A2E)
private val DarkSurface = Color(0xFF16213E)
private val LightBackground = Color(0xFFF5F5F5)
private val LightSurface = Color(0xFFFFFFFF)

private val LightColors = lightColorScheme(
    primary = PortalGreen,
    onPrimary = Color.White,
    primaryContainer = PortalGreen.copy(alpha = 0.15f),
    onPrimaryContainer = PortalGreenDark,
    background = LightBackground,
    surface = LightSurface,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = PortalGreen,
    onPrimary = Color.Black,
    primaryContainer = PortalGreenDark,
    onPrimaryContainer = PortalGreen,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun EvalP3Theme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
