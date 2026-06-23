package com.example.navhigh.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.navhigh.ui.home.HomeFeedScreen
import com.example.navhigh.ui.search.SearchScreen

// Defined explicitly to prevent variable inference failure
sealed interface Screen {
    object Home : Screen
    object Search : Screen
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    when (currentScreen) {
        Screen.Home -> {
            HomeFeedScreen(
                onNavigate = { route: String ->
                    if (route == "Search") {
                        currentScreen = Screen.Search
                    }
                }
            )
        }
        Screen.Search -> {
            SearchScreen(
                onNavigate = { route: String ->
                    if (route == "Home") {
                        currentScreen = Screen.Home
                    }
                }
            )
        }
    }
}