package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Theme colors
val SecondaryBlue = Color(0xFF3DA9FC)
val TextGray = Color(0xFF808080)

@Composable
fun CategoryTabs(modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit = {}
) {

    val tabs = listOf(
        "All",
        "Audio",
        "Creators",
        "Playlists",
        "Hashtags"
    )

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            edgePadding = 16.dp,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = SecondaryBlue,
                    height = 3.dp
                )
            }
        ) {

            tabs.forEachIndexed { index, title ->

                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                        onTabSelected(index)
                    },
                    selectedContentColor = SecondaryBlue,
                    unselectedContentColor = TextGray,
                    text = {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = if (selectedTab == index)
                                FontWeight.SemiBold
                            else
                                FontWeight.Medium
                        )
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390
)
@Composable
fun CategoryTabsPreview() {
    MaterialTheme {
        CategoryTabs()
    }
}