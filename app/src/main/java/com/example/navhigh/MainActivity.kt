package com.example.navhigh

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.navhigh.ui.home.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.parseColor("#000814")
        window.navigationBarColor = Color.parseColor("#000814")

        setContent {
            MainScreen()
        }
    }
}