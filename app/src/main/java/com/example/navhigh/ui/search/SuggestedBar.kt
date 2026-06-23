package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.BorderChip
import com.example.navhigh.ui.theme.CardBlue

@Composable
fun SuggestedForYou() {
    Column {
        Text(
            text = "Suggested For You",
            fontSize = AppTypography.TitleSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2 Rows, 4 Columns grid
        val suggestions = listOf(
            "Chill Vibes", "Sad Songs", "Focus Music", "#motivation",
            "Podcast", "Story Time", "LoFi", "#india"
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            for (i in 0 until 2) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (j in 0 until 4) {
                        val index = i * 4 + j
                        if (index < suggestions.size) {
                            SuggestionChip(text = suggestions[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SuggestionChip(text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(AppDimensions.RadiusMax))
            .background(CardBlue)
            .border(1.dp, BorderChip, RoundedCornerShape(AppDimensions.RadiusMax))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = AppTypography.BodySmall,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}
@Preview(
    name = "Suggested For You",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 390,
    heightDp = 250
)
@Composable
fun SuggestedForYouPreview() {
    MaterialTheme {
        SuggestedForYou()
    }
}