package com.example.navhigh.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.models.Story
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.PrimaryBlue

@Composable
fun StorySection() {
    val stories = listOf(
        Story(1, "Your Story", R.drawable.robo),
        Story(2, "Arjun Beats", R.drawable.pro1_img),
        Story(3, "Ivana Voice", R.drawable.ivana),
        Story(4, "MusiqLab", R.drawable.music),
        Story(5, "EchoFlow", R.drawable.logo),
        Story(6, "See All", R.drawable.img)
    )

    LazyRow(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF030712))
    ) {
        items(stories) { story ->
            StoryItem1(
                image = story.image,
                name = story.name
            )
        }
    }
}

@Composable
fun StoryItem1(image: Int, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(68.dp)
    ) {
        // Base container allowing bottom-right overlay stacking
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // Main Profile Image Avatar Ring
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, PrimaryBlue, CircleShape)
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

            // Check if this item is the user's personal story entry slot
            if (name == "Your Story") {
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Add Story",
                    modifier = Modifier
                        .size(20.dp) // Proportional badge sizing matching reference screenshots
                        .background(Color(0xFF030712), CircleShape) // Seamless cutout overlap base ring background
                        .padding(1.dp)
                        .clip(CircleShape)
                )
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

// ==========================================
// PREVIEW LAYOUT SYSTEM
// ==========================================
@Preview(name = "Story Section Preview", showBackground = true, backgroundColor = 0xFF030712)
@Composable
fun StorySectionPreview() {
    NavHighTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF030712))
        ) {
            StorySection()
        }
    }
}