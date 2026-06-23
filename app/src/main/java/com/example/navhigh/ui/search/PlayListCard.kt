package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
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
import com.example.navhigh.ui.theme.CardBlue

@Composable
fun PlaylistCard(title: String, metadata: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimensions.PlaylistCardHeight)
            .clip(RoundedCornerShape(AppDimensions.Radius16))
            .background(CardBlue)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(AppDimensions.PlaylistCoverSize)
                .clip(RoundedCornerShape(AppDimensions.Radius12))
                .background(Color.Blue.copy(alpha = 0.3f)) // Placeholder
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = title,
                fontSize = AppTypography.TitleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(text = metadata, fontSize = AppTypography.BodySmall, color = Color.White)
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "View Playlist",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
    }
}


@Preview(
    name = "Playlist Card",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 120
)
@Composable
fun PlaylistCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(20.dp)
        ) {
            PlaylistCard(
                title = "Chill Vibes",
                metadata = "24 Tracks • 2h 15m"
            )
        }
    }
}
