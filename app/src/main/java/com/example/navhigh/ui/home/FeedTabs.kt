package com.example.navhigh.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.DividerColor
import com.example.navhigh.ui.theme.PrimaryBlue

@Composable
fun FeedTabs() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            TabText(
                text = "For You",
                selected = true
            )
            TabText(
                text = "Following",
                selected = false
            )
            TabText(
                text = "Trending",
                selected = false
            )
        }

        HorizontalDivider(
            color = DividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun TabText(
    text: String,
    selected: Boolean
) {
    Text(
        text = text,
        color = if (selected) PrimaryBlue else Color.Gray,
        fontSize = 16.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier.padding(end = 24.dp, bottom = 8.dp)
    )
}
