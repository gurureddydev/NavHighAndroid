package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.BorderBlue
import com.example.navhigh.ui.theme.SearchBarEnd
import com.example.navhigh.ui.theme.SearchBarStart
import com.example.navhigh.ui.theme.TextGray

import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimensions.SearchBarHeight)
            .clip(RoundedCornerShape(AppDimensions.Radius16))
            .background(
                Brush.linearGradient(
                    colors = listOf(SearchBarStart, SearchBarEnd)
                )
            )
            .border(1.dp, BorderBlue, RoundedCornerShape(AppDimensions.Radius16)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = TextGray,
                modifier = Modifier
                    .padding(start = 18.dp)
                    .size(AppDimensions.IconSize22)
            )

            Text(
                text = "Search audio, creators, playlists, hashtags...",
                color = TextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            )

            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Voice Search",
                tint = TextGray,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .size(AppDimensions.IconSize22)
            )
        }
    }
}



@Preview(
    name = "Search Bar",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 100
)
@Composable
fun SearchBarPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            SearchBar()
        }
    }
}