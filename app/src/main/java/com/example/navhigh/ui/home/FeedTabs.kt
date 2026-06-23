
package com.example.navhigh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.PrimaryBlue

@Composable
fun FeedTabs() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("For You", "Following", "Trending")

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color(0xFF030712), // Matching the dark background from image_a33a25.jpg
        contentColor = PrimaryBlue,
        // Customizes the full width divider at the bottom of the row
        divider = {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFF111827))
            )
        },
        // Customizes the active selected blue indicator bar
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 2.dp,
                    color = PrimaryBlue
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTabIndex == index

            Tab(
                selected = isSelected,
                onClick = { selectedTabIndex = index },
                text = {
                    Text(
                        text = title,
                        color = if (isSelected) PrimaryBlue else Color(0xFF9CA3AF),
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}

// ==========================================
// PREVIEW LAYOUT 
// ==========================================
@Preview(name = "Perfect Feed Tabs Preview", showBackground = true, backgroundColor = 0xFF030712)
@Composable
fun FeedTabsPreview() {
    NavHighTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF030712))
        ) {
            FeedTabs()
        }
    }
}
