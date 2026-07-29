package com.example.navhigh.ui.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.TopHeaderAccentBlueColor

@Composable
fun FeedTabs(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val density = LocalDensity.current

    // Stores each tab's measured x-offset and width (in Dp), keyed by tab index.
    val tabOffsets = remember { mutableStateMapOf<Int, Dp>() }
    val tabWidths = remember { mutableStateMapOf<Int, Dp>() }

    val indicatorOffset by animateDpAsState(
        targetValue = tabOffsets[selectedTabIndex] ?: 0.dp,
        animationSpec = tween(durationMillis = 250),
        label = "indicatorOffset"
    )
    val indicatorWidth by animateDpAsState(
        targetValue = tabWidths[selectedTabIndex] ?: 44.dp,
        animationSpec = tween(durationMillis = 250),
        label = "indicatorWidth"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            tabs.forEachIndexed { index, tabName ->
                val isSelected = index == selectedTabIndex
                Column(
                    modifier = Modifier
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                        .onGloballyPositioned { coordinates ->
                            tabOffsets[index] = with(density) { coordinates.positionInParent().x.toDp() }
                            tabWidths[index] = with(density) { coordinates.size.width.toDp() }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = tabName,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Single sliding indicator — uses start padding (not offset) to move smoothly between tabs
        Box(
            modifier = Modifier
                .padding(PaddingValues(start = indicatorOffset))
                .height(2.dp)
                .width(indicatorWidth)
                .background(TopHeaderAccentBlueColor, RoundedCornerShape(1.dp))
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun FeedTabsPreview() {
    var selectedIndex by remember { mutableStateOf(0) }

    val tabs = listOf("For You", "Following", "Trending")

    FeedTabs(
        tabs = tabs,
        selectedTabIndex = selectedIndex,
        onTabSelected = { index -> selectedIndex = index }
    )
}
