package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.BorderChip
import com.example.navhigh.ui.theme.CardBlue
import com.example.navhigh.ui.theme.PrimaryBlue
import com.example.navhigh.ui.theme.SecondaryText

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    name = "Recent Searches",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 180
)
@Composable
fun RecentSearchesPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            RecentSearches()
        }
    }
}

@Composable
fun RecentSearches() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Searches",
                fontSize = AppTypography.TitleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                text = "Clear All",
                fontSize = AppTypography.BodyMedium,
                color = PrimaryBlue
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val items = listOf("Lofi Beats", "Motivation", "#podcast", "Arjun Beats")
            items(items) { item ->
                RecentSearchChip(text = item)
            }
        }
    }
}

@Composable
fun RecentSearchChip(text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(AppDimensions.RadiusMax))
            .background(CardBlue)
            .border(1.dp, BorderChip, RoundedCornerShape(AppDimensions.RadiusMax))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            fontSize = AppTypography.BodySmall,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            tint = SecondaryText,
            modifier = Modifier.size(14.dp)
        )
    }
}