package com.example.navhigh.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NavHighColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    background = Color(0xFF000814),
    surface = Color(0xFF000814),

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun NavHighTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NavHighColorScheme,
        typography = Typography,
        content = content
    )
}