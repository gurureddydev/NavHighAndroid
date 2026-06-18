package com.example.navhigh.ui.home

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.CardBlue
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.PrimaryBlue

@Composable
fun AudioPostCard(
    title: String = "Late night thoughts 🌙🎧",
    tags: String = "#lofi #chill #vibes",
    audioResId: Int? = null,
    autoPlay: Boolean = false
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    val mediaPlayer = remember(audioResId) {
        if (audioResId != null) {
            try {
                MediaPlayer.create(context, audioResId).apply {
                    setOnCompletionListener {
                        isPlaying = false
                    }
                }
            } catch (e: Exception) {
                null
            }
        } else null
    }

    // Auto-play logic when the card loads
    LaunchedEffect(mediaPlayer) {
        if (autoPlay && mediaPlayer != null) {
            mediaPlayer.start()
            isPlaying = true
        }
    }

    DisposableEffect(audioResId) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBlue
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Header
            PostHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // Artwork + Play Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(24.dp))
            ) {

                // Background image
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = .8f)
                                )
                            )
                        )
                )

                // Play/Pause button
                FloatingActionButton(
                    onClick = {
                        mediaPlayer?.let {
                            if (it.isPlaying) {
                                it.pause()
                                isPlaying = false
                            } else {
                                it.start()
                                isPlaying = true
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.Center),
                    containerColor = PrimaryBlue
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White
                    )
                }

                // Listen count
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color.Black.copy(alpha = .4f))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Headset,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "1.2K",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                // Floating lyrics / captions
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 24.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Previous line
                    Text(
                        text = "Sometimes the best ideas",
                        color = Color.White.copy(alpha = .5f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Current line (highlighted)
                    Text(
                        text = "come after midnight",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Next line
                    Text(
                        text = "when everything is quiet...",
                        color = Color.White.copy(alpha = .5f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Waveform
            AudioWaveform()

            Spacer(modifier = Modifier.height(8.dp))

            // Time
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "00:18",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "01:00",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Tags
            Text(
                text = tags,
                color = PrimaryBlue,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Actions
            PostActions()
        }
    }
}

@Preview(
    name = "Audio Reel Card",
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF0F172A
)
@Composable
fun AudioPostCardPreview() {
    NavHighTheme {
        Surface(
            color = Color(0xFF0F172A)
        ) {
            AudioPostCard(audioResId = R.raw.audio_1)
        }
    }
}
