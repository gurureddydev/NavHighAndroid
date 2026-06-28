package com.example.navhigh

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowInsetsControllerCompat
import com.example.navhigh.navigation.MainScreen
import com.example.navhigh.ui.theme.NavHighTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        window.statusBarColor = Color.parseColor("#000814")
        window.navigationBarColor = Color.parseColor("#000814")

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

        setContent {
            NavHighTheme {
                MainScreen()
            }
        }
    }
}