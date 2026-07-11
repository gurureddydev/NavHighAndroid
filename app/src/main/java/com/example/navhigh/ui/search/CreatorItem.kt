package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.navhigh.ui.theme.SecondaryBlue
import com.example.navhigh.ui.theme.TextGrayDark

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreatorItem(name: String, handle: String, followers: String, isVerified: Boolean) {
    Row(modifier = Modifier
            .fillMaxWidth()
            .height(AppDimensions.CreatorItemHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
                .size(AppDimensions.CreatorAvatarSize)
                .clip(CircleShape)
                .background(
                    Brush.sweepGradient(
                        colors = listOf(PrimaryBlue, SecondaryBlue, Color.Magenta, PrimaryBlue)
                    )
                )
                .padding(2.dp)
                .clip(CircleShape)
                .background(Color.Black) // Placeholder for image
        )

        Column(modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    fontSize = AppTypography.TitleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                if (isVerified) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint = PrimaryBlue,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp)
                    )
                }
            }
            Text(text = handle, fontSize = AppTypography.BodySmall, color = TextGrayDark)
            Text(text = followers, fontSize = AppTypography.BodySmall, color = TextGrayDark)
        }

        Button(
            onClick = { },
            modifier = Modifier
                .width(118.dp)
                .height(42.dp)
                .border(1.5.dp, PrimaryBlue, RoundedCornerShape(21.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Follow",
                fontSize = AppTypography.TitleSmall,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More",
            tint = Color.White,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Preview(
    name = "Creator Item",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 120
)
@Composable
fun CreatorItemPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            CreatorItem(
                name = "Arjun Beats",
                handle = "@arjunbeats",
                followers = "1.2M Followers",
                isVerified = true
            )
        }
    }
}