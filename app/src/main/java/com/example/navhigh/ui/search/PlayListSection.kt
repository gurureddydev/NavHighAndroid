package com.example.navhigh.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PlaylistSection1() {
    Column {
        SectionHeader(title = "Playlists")

        Spacer(modifier = Modifier.height(16.dp))

        PlaylistCard(title = "Chill LoFi Mix", metadata = "22 Tracks • by MusicLab")
    }
}

@Preview(
    name = "Playlist Section",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 220
)
@Composable
fun PlaylistSection1Preview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            PlaylistSection1()
        }
    }
}