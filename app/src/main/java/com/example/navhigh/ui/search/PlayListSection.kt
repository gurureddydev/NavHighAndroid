package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Main Container ---
@Composable
fun PlaylistSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Section Header
        Text(
            text = "Playlists",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Using the unique, renamed component
        PlaylistEntryCard(title = "Chill LoFi Mix", metadata = "22 Tracks • by MusicLab")
    }
}

// --- Renamed to prevent conflict ---
@Composable
fun PlaylistEntryCard(title: String, metadata: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thumbnail placeholder
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF3DA9FC))
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = metadata, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

// --- Preview ---
@Preview(
    name = "Playlist Section",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
fun PlaylistSectionPreview() {
    MaterialTheme {
        Surface(color = Color(0xFF121212)) {
            PlaylistSection()
        }
    }
}