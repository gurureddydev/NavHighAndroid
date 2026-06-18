package com.example.navhigh.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navhigh.R
import com.example.navhigh.models.Story

@Composable
fun StorySection() {
    val stories = listOf(
        Story(1, "Your Story", R.drawable.ic_launcher_foreground),
        Story(2, "Arjun Beats", R.drawable.ic_launcher_foreground),
        Story(3, "Ivana Voice", R.drawable.ic_launcher_foreground),
        Story(4, "MusiqLab", R.drawable.ic_launcher_foreground),
        Story(5, "EchoFlow", R.drawable.ic_launcher_foreground)
    )

    LazyRow(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(stories) { story ->
            StoryItem(
                image = story.image,
                name = story.name
            )
        }
    }
}
