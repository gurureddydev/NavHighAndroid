package com.example.navhigh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.PrimaryBlue

@Composable
fun AudioWaveform(
    progress: Float = 0.4f, // 40% played
    modifier: Modifier = Modifier
) {

    val waveform = listOf(
        10, 18, 32, 42, 26, 20, 38, 14,
        24, 40, 16, 28, 36, 18, 44, 22,
        14, 32, 26, 40, 18, 12, 34, 42,
        20, 28, 36, 16, 24, 40, 18, 30,
        44, 20, 12, 32, 26, 38, 22, 16
    )

    val playedBars = (waveform.size * progress).toInt()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        waveform.forEachIndexed { index, height ->

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(height.dp)
                    .clip(CircleShape)
                    .background(
                        if (index <= playedBars)
                            PrimaryBlue
                        else
                            Color(0xFF374151)
                    )
            )
        }
    }
}