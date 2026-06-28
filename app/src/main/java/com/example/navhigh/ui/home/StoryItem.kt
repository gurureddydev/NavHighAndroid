package com.example.navhigh.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.TopHeaderAccentBlueColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview

val ScreenBackgroundDarkColor = Color(0xFF02070D)

@Composable
fun StoryItem1(image: Int, name: String, isLive: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(68.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = if (isLive) Color(0xFFFF0055) else TopHeaderAccentBlueColor,
                        shape = CircleShape
                    )
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            if (name == "Your Story") {
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Add Story",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(20.dp)
                        .background(ScreenBackgroundDarkColor, CircleShape)
                        .padding(1.dp)
                        .clip(CircleShape)
                )
            } else if (isLive) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFF0055), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = "LIVE",
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = name,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Preview(showBackground = true)
@Composable
fun StoryItemPreview() {
    // Wrapping in a surface with your app's background color
    Surface(color = ScreenBackgroundDarkColor) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Standard Story
            StoryItem1(
                image = R.drawable.ic_launcher_foreground,
                name = "Alice",
                isLive = false,
                onClick = {}
            )

            // Live Story
            StoryItem1(
                image = R.drawable.ic_launcher_foreground,
                name = "Live Streamer",
                isLive = true,
                onClick = {}
            )

            // "Your Story" with plus icon
            StoryItem1(
                image = R.drawable.ic_launcher_foreground,
                name = "Your Story",
                isLive = false,
                onClick = {}
            )
        }
    }
}