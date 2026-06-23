package com.example.navhigh.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.PrimaryBlue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SectionHeader1(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = AppTypography.TitleSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            text = "See All",
            fontSize = AppTypography.BodyMedium,
            color = PrimaryBlue
        )
    }
}

@Preview(
    name = "Section Header",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 80
)
@Composable
fun SectionHeader1Preview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            SectionHeader1(
                title = "Trending Creators"
            )
        }
    }
}