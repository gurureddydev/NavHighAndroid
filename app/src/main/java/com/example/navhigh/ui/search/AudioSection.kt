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
fun AudioSection() {
    Column {
        SectionHeader(title = "Audio")

        Spacer(modifier = Modifier.height(16.dp))

        AudioRow(
            title = "Midnight Thoughts",
            creator = "Arjun Beats",
            playCount = "12.6K",
            duration = "00:58"
        )

        Spacer(modifier = Modifier.height(14.dp))

        AudioRow(
            title = "Late Night Drive",
            creator = "EchoFlow",
            playCount = "9.8K",
            duration = "00:45"
        )

        Spacer(modifier = Modifier.height(14.dp))

        AudioRow(
            title = "Broken but Healing",
            creator = "Ivana Voice",
            playCount = "7.4K",
            duration = "01:00"
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Preview(
    name = "Audio Section",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390,
    heightDp = 520
)
@Composable
fun AudioSectionPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            AudioSection()
        }
    }
}