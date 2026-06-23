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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.PrimaryBlue
import com.example.navhigh.ui.theme.TextGrayLight

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun AudioRow(title: String, creator: String, playCount: String, duration: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimensions.AudioRowHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(AppDimensions.AudioThumbnailSize)
                .clip(RoundedCornerShape(AppDimensions.Radius12))
                .background(Color.DarkGray) // Placeholder
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = AppTypography.TitleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Headset,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = playCount,
                        fontSize = AppTypography.BodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 8.dp).size(20.dp)
                    )
                }
            }
            Text(text = creator, fontSize = AppTypography.BodySmall, color = TextGrayLight)

            Spacer(modifier = Modifier.height(8.dp))

            // Waveform placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(PrimaryBlue.copy(alpha = 0.5f), PrimaryBlue)
                        )
                    )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = duration, fontSize = AppTypography.BodySmall, color = TextGrayLight)
            }
        }
    }
}


@Preview(
    name = "Audio Row",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 160
)
@Composable
fun AudioRowPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            AudioRow(
                title = "Midnight LoFi Session",
                creator = "MusicLab Studio",
                playCount = "12.4K",
                duration = "03:42"
            )
        }
    }
}