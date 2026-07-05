package com.example.navhigh.ui.splashScreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NavTextScreen(progress = 0.6f)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}