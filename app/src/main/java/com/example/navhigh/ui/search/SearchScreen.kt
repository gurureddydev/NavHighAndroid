package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.*

@Composable
fun SearchScreen(onNavigate: (String) -> Unit = {}) {
    val recentSearches = remember { mutableStateListOf("Lofi Beats", "Motivation", "#podcast") }

    Scaffold(containerColor = Background
    ) { paddingValues ->
        // Applying paddingValues for system UI + your custom 5.dp padding
        LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(9.dp)) {
            item { SearchHeader() }
            item { SearchBar(onSearch = { query -> if (query.isNotBlank() && !recentSearches.contains(query)) {
                        recentSearches.add(0, query) } }) }
            item { Spacer(modifier = Modifier.height(10.dp))
                RecentSearches(
                    items = recentSearches,
                    onClearAll = { recentSearches.clear() },
                    onRemoveItem = { item -> recentSearches.remove(item) }) }
            item { Spacer(modifier = Modifier.height(5.dp))
                SuggestedForYou()
            }
            item { Spacer(modifier = Modifier.height(5.dp))
                CategoryTabs() }
            item { Spacer(modifier = Modifier.height(5.dp))
                CreatorSection()
            }
            item { Spacer(modifier = Modifier.height(2.dp))
                AudioSection()
            }
            item { Spacer(modifier = Modifier.height(AppDimensions.Padding28))
                PlaylistSection()
            }
            item { Spacer(modifier = Modifier.height(AppDimensions.Padding32))
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