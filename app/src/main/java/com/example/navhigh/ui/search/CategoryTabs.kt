package com.example.navhigh.ui.search

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.DividerBlue
import com.example.navhigh.ui.theme.SecondaryBlue
import com.example.navhigh.ui.theme.TextGray

import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CategoryTabs() {
    val tabs = listOf("All", "Audio", "Creators", "Playlists", "Hashtags")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimensions.TabHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 12.dp.value.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (isSelected) SecondaryBlue else TextGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(64.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(SecondaryBlue)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(3.dp))
                    }
                }
            }
        }
        HorizontalDivider(thickness = 1.dp, color = DividerBlue)
    }
}


@Preview(
    name = "Category Tabs",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 100
)
@Composable
fun CategoryTabsPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            CategoryTabs()
        }
    }
}