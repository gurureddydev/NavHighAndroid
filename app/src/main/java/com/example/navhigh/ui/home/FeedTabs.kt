package com.example.navhigh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.navhigh.ui.theme.TopHeaderAccentBlueColor


@Composable
fun FeedTabs(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        tabs.forEachIndexed { index, tabName ->
            val isSelected = index == selectedTabIndex
            Column(
                modifier = Modifier
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 14.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tabName,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(44.dp)
                        .background(if (isSelected) TopHeaderAccentBlueColor else Color.Transparent, RoundedCornerShape(1.dp))
                )
            }
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF000000) // Setting a dark background to see the White text
@Composable
fun FeedTabsPreview() {
    // We use remember to simulate the state changing
    var selectedIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Home", "Explore", "Notifications")

    FeedTabs(
        tabs = tabs,
        selectedTabIndex = selectedIndex,
        onTabSelected = { index -> selectedIndex = index }
    )
}
