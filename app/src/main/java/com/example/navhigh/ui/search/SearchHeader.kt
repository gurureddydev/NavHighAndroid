package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.AccentBlue
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography

@Composable
fun SearchHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()

            .height(AppDimensions.HeaderHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Search",
            fontSize = AppTypography.TitleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // Notification Icon with Badge
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier
                    .size(AppDimensions.IconSize24)
                    // Adding padding here pushes the icon slightly away from the corner,
                    // making room for the badge without using offset.
                    .padding(top = 2.dp, end = 2.dp)
            )

            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(AccentBlue),
                // This ensures the content is mathematically centered in the box
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "3",
                    fontSize = AppTypography.BadgeText,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    // Ensure the text line height or padding isn't pushing it off-center
                    lineHeight = 10.sp
                )
            }

            }
        }
    }


@Preview(
    name = "Search Header",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 100
)
@Composable
fun SearchHeaderPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            SearchHeader()
        }
    }
}