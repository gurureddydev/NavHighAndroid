package com.example.navhigh.ui.search

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import kotlinx.coroutines.delay

data class AudioItem(
    val title: String,
    val creator: String,
    val playCount: String,
    val imageRes: Int,
    val audioRes: Int
)

@Composable
fun AudioSection() {

    var playingIndex by remember {
        mutableStateOf(-1)
    }

    var activePlayer by remember {
        mutableStateOf<MediaPlayer?>(null)
    }

    var showAll by remember {
        mutableStateOf(false)
    }

    val audioList = listOf(

        AudioItem(
            "Midnight Thoughts",
            "Arjun Beats",
            "12.6K",
            R.drawable.pro1_img,
            R.raw.got
        ),

        AudioItem(
            "Late Night Drive",
            "EchoFlow",
            "9.8K",
            R.drawable.logo,
            R.raw.motivation
        ),

        AudioItem(
            "Broken but Healing",
            "Ivana Voice",
            "7.4K",
            R.drawable.ivana,
            R.raw.jack
        ),

        AudioItem(
            "23 Theme",
            "MusicLab",
            "5.2K",
            R.drawable.music,
            R.raw.aa23
        )
    )

    val visibleAudioList =
        if (showAll)
            audioList
        else
            audioList.take(3)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Audio",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text =
                    if (showAll)
                        "Show Less"
                    else
                        "See All",

                color = Color(0xFF3DA9FC),
                fontSize = 14.sp,

                modifier = Modifier.clickable {
                    showAll = !showAll
                }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        visibleAudioList.forEachIndexed { index, item ->

            AudioRowItem(
                item = item,
                itemIndex = index,
                playingIndex = playingIndex,
                activePlayer = activePlayer,
                onPlayerChange = {
                    activePlayer = it
                },
                onPlayingIndexChange = {
                    playingIndex = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun AudioRowItem(
    item: AudioItem,
    itemIndex: Int,
    playingIndex: Int,
    activePlayer: MediaPlayer?,
    onPlayerChange: (MediaPlayer?) -> Unit,
    onPlayingIndexChange: (Int) -> Unit
) {

    val context = LocalContext.current

    var mediaPlayer by remember {
        mutableStateOf<MediaPlayer?>(null)
    }

    var currentTime by remember {
        mutableStateOf(0)
    }

    val isPlaying = playingIndex == itemIndex

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    LaunchedEffect(playingIndex) {

        while (playingIndex == itemIndex) {

            mediaPlayer?.let {
                currentTime = it.currentPosition / 1000
            }

            delay(500)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {

            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-4).dp, y = (-4).dp)
                    .background(
                        Color.Black.copy(alpha = 0.7f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector =
                        if (isPlaying)
                            Icons.Default.Pause
                        else
                            Icons.Default.PlayArrow,

                    contentDescription = null,
                    tint = Color.White,

                    modifier = Modifier
                        .size(20.dp)
                        .clickable {

                            if (isPlaying) {

                                mediaPlayer?.pause()
                                mediaPlayer?.seekTo(0)

                                currentTime = 0

                                onPlayingIndexChange(-1)

                            } else {

                                activePlayer?.let {

                                    try {
                                        it.stop()
                                    } catch (_: Exception) {
                                    }

                                    it.release()
                                }

                                mediaPlayer?.release()

                                mediaPlayer =
                                    MediaPlayer.create(
                                        context,
                                        item.audioRes
                                    )

                                mediaPlayer?.setOnCompletionListener {

                                    currentTime = 0
                                    onPlayingIndexChange(-1)
                                }

                                mediaPlayer?.start()

                                onPlayerChange(mediaPlayer)
                                onPlayingIndexChange(itemIndex)
                            }
                        }
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = item.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color(0xFF3DA9FC),
                    modifier = Modifier.size(14.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = item.playCount,
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = item.creator,
                color = Color.Gray,
                fontSize = 10.sp,
                lineHeight = 10.sp
            )

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                val waveformHeights = listOf(
                    4,5,6,8,10,12,14,12,10,8,
                    6,5,4,6,8,12,18,24,18,12,
                    8,6,4,5,7,10,14,20,28,20,
                    14,10,7,5,4,6,8,12,18,24,
                    18,12,8,6,4,5,6,8,10,12,
                    10,8,6,5,4
                )

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    waveformHeights.forEach { height ->

                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(height.dp)
                                .background(
                                    Color(0xFF00A3FF),
                                    RoundedCornerShape(2.dp)
                                )
                        )

                        Spacer(modifier = Modifier.width(1.dp))
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = String.format(
                        "%02d:%02d",
                        currentTime / 60,
                        currentTime % 60
                    ),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
fun AudioSectionPreview() {

    MaterialTheme {

        Surface(
            color = Color(0xFF121212)
        ) {
            AudioSection()
        }
    }
}