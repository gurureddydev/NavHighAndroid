package com.example.navhigh.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.SecondaryText
import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 82.dp
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xCC030B18)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Artwork placeholder
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp)),
                color = Color.DarkGray
            ) {
                // Image could go here
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Late night thoughts",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Arjun Beats",
                    color = SecondaryText,
                    fontSize = 14.sp
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            // Pause Button Placeholder
            IconButton(onClick = { }) {
                // Use a standard material icon or placeholder
                Text("⏸️", color = Color.White)
            }

            // Playlist Button Placeholder
            IconButton(onClick = { }) {
                Text("🎶", color = Color.White)
            }
        }
    }
}


@Preview(
    name = "Mini Player Light",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 390,
    heightDp = 844
)
@Composable
fun MiniPlayerPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MiniPlayer(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(
    name = "Mini Player Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 390,
    heightDp = 844
)
@Composable
fun MiniPlayerDarkPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MiniPlayer(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}