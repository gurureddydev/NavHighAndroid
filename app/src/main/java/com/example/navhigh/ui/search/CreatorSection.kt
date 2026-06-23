package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreatorSection() {
    Column {
        SectionHeader1(title = "Creators")

        Spacer(modifier = Modifier.height(16.dp))

        CreatorItem(
            name = "Arjun Beats",
            handle = "@arjunbeats",
            followers = "128K Followers",
            isVerified = true
        )

        CreatorItem(
            name = "Ivana Voice",
            handle = "@ivanavoice",
            followers = "96K Followers",
            isVerified = true
        )

        CreatorItem(
            name = "MusicLab",
            handle = "@musiclab",
            followers = "74K Followers",
            isVerified = true
        )
    }
}

@Composable
fun SectionHeader2(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

@Preview(
    name = "Creator Section",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 420
)
@Composable
fun CreatorSectionPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            CreatorSection()
        }
    }
}