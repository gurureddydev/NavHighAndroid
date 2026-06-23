package com.example.navhigh.ui.search


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.ui.home.BottomNavigationBar
import com.example.navhigh.ui.theme.*

@Composable
fun SearchScreen(onNavigate: (String) -> Unit = {}) {
    Scaffold(
        containerColor = Background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = AppDimensions.PaddingExtraLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.Padding28)
        ) {
            item {
                SearchHeader()
            }
            item {
                SearchBar()
            }
            item {
                RecentSearches()
            }
            item {
                SuggestedForYou()
            }
            item {
                CategoryTabs()
            }
            item {
                CreatorSection()
            }
            item {
                AudioSection()
            }
            item {
                PlaylistSection1()
            }
            item {
                Spacer(modifier = Modifier.height(AppDimensions.Padding32))
            }
        }
    }
}
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun SearchScreenPreview() {
    NavHighTheme {
        SearchScreen()
    }
}
