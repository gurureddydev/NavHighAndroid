package com.example.navhigh.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.PrimaryBlue
import com.example.navhigh.ui.theme.Purple
import androidx.compose.foundation.background
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun StoryItem(
    image: Int,
    name: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(64.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                PrimaryBlue,
                                Purple
                            )
                        ),
                        shape = CircleShape
                    )
            )

            // Using ic_launcher_foreground as a placeholder if the specific image isn't available
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun StoryItemPreview() {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        StoryItem(
            image = R.drawable.ic_launcher_foreground,
            name = "Jessica"
        )
    }
}