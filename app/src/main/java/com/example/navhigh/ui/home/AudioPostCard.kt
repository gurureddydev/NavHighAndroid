@file:Suppress("SpellCheckingInspection")

package com.example.navhigh.ui.home

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

val CardBgDark = Color(0xFF070E17)
val PlayerSurfaceBg = Color(0xFF0F1C2C)
val WaveformInactiveColor = Color(0xFF4B5563)
val CustomBorderColor = Color(0xFF1E293B)

val FollowButtonBlue = Color(0xFF0056B3)
val FollowBorderBlue = Color(0xFF00B2FE)

// --- DATA STRUCTURE FOR PLAYBACK AUDIO TRACKS ---
data class AudioTrackItem(
    val id: Int,
    val profileName: String,
    val username: String,
    val timeAgo: String,
    val profileResId: Int,
    val title: String,
    val tags: String,
    val artworkResId: Int,
    val audioResId: Int,
    val playsCount: String,
    val accentColor: Color
)

// --- CONTINUOUS BACKGROUND CONTAINER & AUTO-ADVANCE COMPOSABLE ---
@Composable
fun AutoAdvancingAudioFeed(
    tracks: List<AudioTrackItem>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var currentTrackIndex by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) } // Automatically runs audio on open
    var currentPlaybackPosition by remember { mutableIntStateOf(0) }
    var trackTotalDuration by remember { mutableIntStateOf(120000) }

    // Re-instantiate/update player context explicitly when active target track index increments
    val activeTrack = remember(currentTrackIndex, tracks) {
        if (tracks.isNotEmpty()) tracks[currentTrackIndex] else null
    }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    // Initialize player safely outside the recycled components
    LaunchedEffect(activeTrack) {
        if (activeTrack != null && activeTrack.audioResId != 0) {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()

                mediaPlayer = MediaPlayer.create(context, activeTrack.audioResId).apply {
                    trackTotalDuration = duration
                    seekTo(currentPlaybackPosition.coerceAtMost(duration))

                    setOnCompletionListener {
                        // Forward tracking logic safely boundaries checked
                        if (currentTrackIndex + 1 < tracks.size) {
                            currentPlaybackPosition = 0
                            currentTrackIndex += 1
                            isPlaying = true
                        } else {
                            isPlaying = false
                            seekTo(0)
                            currentPlaybackPosition = 0
                        }
                    }
                }

                if (isPlaying) {
                    mediaPlayer?.start()
                }
            } catch (e: Exception) {
                Log.e("AutoAdvancingAudioFeed", "Error initializing structural media element", e)
            }
        }
    }

    // Handles smooth list layout updates automatically alongside active track adjustments
    LaunchedEffect(currentTrackIndex) {
        scope.launch {
            listState.animateScrollToItem(currentTrackIndex)
        }
    }

    // Local player clock syncer mechanism loop
    LaunchedEffect(isPlaying, mediaPlayer, currentTrackIndex) {
        if (isPlaying && mediaPlayer != null) {
            while (isPlaying) {
                try {
                    if (mediaPlayer?.isPlaying == true) {
                        currentPlaybackPosition = mediaPlayer?.currentPosition ?: 0
                    }
                } catch (e: Exception) {
                    break
                }
                delay(30)
            }
        }
    }

    // Global toggle controller implementation hooks
    val togglePlayback: (Boolean) -> Unit = { playState ->
        isPlaying = playState
        try {
            if (playState) {
                mediaPlayer?.start()
            } else {
                mediaPlayer?.pause()
            }
        } catch (e: Exception) {
            Log.e("AutoAdvancingAudioFeed", "Error alternating local sound driver parameters", e)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            } catch (e: Exception) {
                Log.e("AutoAdvancingAudioFeed", "Failure tearing down service pipeline handles", e)
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize().background(Color(0xFF02070D)),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(tracks) { index, track ->
            val isActive = index == currentTrackIndex

            AudioPostCardElegant(
                profileName = track.profileName,
                username = track.username,
                timeAgo = track.timeAgo,
                profileResId = track.profileResId,
                title = track.title,
                tags = track.tags,
                artworkResId = track.artworkResId,
                playsCount = track.playsCount,
                accentColor = track.accentColor,
                isGlobalPlaying = isActive && isPlaying,
                currentPosition = if (isActive) currentPlaybackPosition else 0,
                totalDuration = if (isActive) trackTotalDuration else 120000,
                onPlayToggle = { playRequested ->
                    if (isActive) {
                        togglePlayback(playRequested)
                    } else {
                        // Jump completely contextually to a different card block selection
                        currentPlaybackPosition = 0
                        currentTrackIndex = index
                        isPlaying = true
                    }
                },
                onSeek = { seekTarget ->
                    if (isActive) {
                        currentPlaybackPosition = seekTarget
                        mediaPlayer?.seekTo(seekTarget)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// --- STATELESS UI HOISTED COMPOSABLE AUDIO POST CARD ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPostCardElegant(
    profileName: String,
    username: String,
    modifier: Modifier = Modifier,
    timeAgo: String = "1h ago",
    profileResId: Int,
    title: String,
    tags: String,
    artworkResId: Int = R.drawable.ic_launcher_foreground,
    playsCount: String,
    initialLikes: Int = 1250,
    initialComments: Int = 84,
    accentColor: Color = Color(0xFF00B2FE),
    isGlobalPlaying: Boolean = false,
    currentPosition: Int = 0,
    totalDuration: Int = 120000,
    onPlayToggle: (Boolean) -> Unit = {},
    onSeek: (Int) -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(initialLikes) }
    var isSaved by remember { mutableStateOf(false) }
    var isFollowing by remember { mutableStateOf(false) }

    val progress = remember(currentPosition, totalDuration) {
        if (totalDuration > 0) (currentPosition.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f) else 0f
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBgDark)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = profileResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable { onProfileClick() },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = profileName,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.CheckCircle, null, tint = accentColor, modifier = Modifier.size(14.dp))
                    }
                    Text(text = "$username • $timeAgo", color = Color.Gray, fontSize = 11.sp)
                }

                OutlinedButton(
                    onClick = { isFollowing = !isFollowing },
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .height(34.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(50),
                    border = borderStroke(1.dp, FollowBorderBlue),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = if (isFollowing) "Following" else "Follow",
                        color = FollowBorderBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                    Box(
                        modifier = Modifier
                            .clickable { }
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                layout(placeable.width, placeable.height) {
                                    placeable.placeRelative(14.dp.roundToPx(), 0)
                                }
                            }
                            .padding(vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Normal)
            Text(text = tags, color = accentColor, fontSize = 13.sp, fontWeight = FontWeight.Normal)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PlayerSurfaceBg, RoundedCornerShape(16.dp))
                    .border(1.dp, CustomBorderColor, RoundedCornerShape(16.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = artworkResId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            .clickable { onPlayToggle(!isGlobalPlaying) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isGlobalPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format(Locale.getDefault(), "%02d:%02d", (currentPosition / 1000) / 60, (currentPosition / 1000) % 60),
                            color = accentColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Headset,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = playsCount,
                                color = accentColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    CompactSeekableWaveform(
                        progress = progress,
                        activeColor = accentColor,
                        onSeek = { targetProgress ->
                            val seekTarget = (targetProgress * totalDuration).toInt()
                            onSeek(seekTarget)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable {
                        isLiked = !isLiked
                        if (isLiked) likesCount++ else likesCount--
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isLiked) accentColor else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = likesCount.toString(), color = Color.Gray, fontSize = 12.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = initialComments.toString(), color = Color.Gray, fontSize = 12.sp)
                }

                Image(painter = painterResource(id = R.drawable.refresh), contentDescription = null, modifier = Modifier.size(18.dp), colorFilter = ColorFilter.tint(Color.Gray))
                Image(painter = painterResource(id = R.drawable.send), contentDescription = null, modifier = Modifier.size(18.dp), colorFilter = ColorFilter.tint(Color.Gray))

                Icon(
                    imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = if (isSaved) accentColor else Color.Gray,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { isSaved = !isSaved }
                )
            }
        }
    }
}

@Composable
fun CompactSeekableWaveform(
    progress: Float,
    activeColor: Color,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val barCount = 30
    val waveAmplitudes = remember { floatArrayOf(0.3f,0.4f,0.6f,0.8f,0.5f,0.3f,0.6f,0.9f,0.7f,0.4f,0.5f,0.8f,0.4f,0.3f,0.6f,0.7f,0.9f,0.5f,0.3f,0.4f,0.7f,0.8f,0.5f,0.3f,0.6f,0.5f,0.4f,0.3f,0.2f,0.1f) }
    var width by remember { mutableFloatStateOf(1f) }

    val pointerInputModifier = Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { offset -> onSeek((offset.x / width).coerceIn(0f, 1f)) },
            onDrag = { change, _ -> onSeek((change.position.x / width).coerceIn(0f, 1f)) }
        )
    }

    Canvas(
        modifier = modifier.then(pointerInputModifier)
    ) {
        width = size.width
        val canvasHeight = size.height
        val midY = canvasHeight / 2f

        val gapSpace = 3.dp.toPx()
        val fixedBarWidth = (width - (gapSpace * (barCount - 1))) / barCount

        for (i in 0 until barCount) {
            val barX = i * (fixedBarWidth + gapSpace) + (fixedBarWidth / 2f)
            val calculatedHeight = midY * waveAmplitudes[i]
            val isFilled = barX <= width * progress

            drawLine(
                color = if (isFilled) activeColor else WaveformInactiveColor,
                start = Offset(barX, midY - calculatedHeight),
                end = Offset(barX, midY + calculatedHeight),
                strokeWidth = fixedBarWidth,
                cap = StrokeCap.Round
            )
        }
    }
}

private fun borderStroke(width: androidx.compose.ui.unit.Dp, color: Color) = androidx.compose.foundation.BorderStroke(width, color)

// --- INDIVIDUAL COMPOSABLE HOISTED RENDERING PREVIEWS ---

@Preview(name = "Preview Card 1: Ivana Voice", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun AudioPostCardIvanaPreview() {
    AudioPostCardElegant(
        profileName = "Ivana Voice",
        username = "@ivanavoice",
        timeAgo = "1h ago",
        profileResId = R.drawable.ivana,
        title = "Testing out the new high-fidelity microphone setup. Sounds incredibly clean! 🎙️🎧",
        tags = "#podcast #audio #engineering #miccheck",
        artworkResId = R.drawable.ivana,
        playsCount = "1.2K",
        accentColor = Color(0xFFFFC107),
        isGlobalPlaying = true, // Autoplays smoothly mock visible in dynamic preview layout
        currentPosition = 42000,
        totalDuration = 180000
    )
}

@Preview(name = "Preview Card 2: EchoFlow", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun AudioPostCardEchoFlowPreview() {
    AudioPostCardElegant(
        profileName = "EchoFlow",
        username = "@echoflow",
        profileResId = R.drawable.logo,
        title = "Stop making excuses. Your future self is watching you. Wake up and grind! 🔥💪",
        tags = "#motivation #mindset #discipline #success",
        artworkResId = R.drawable.logo,
        playsCount = "12.5K",
        accentColor = Color(0xFF00FFCC),
        isGlobalPlaying = false,
        currentPosition = 0,
        totalDuration = 60000
    )
}

@Preview(name = "Preview Card 3: Arjun Beats", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun AudioPostCardArjunPreview() {
    AudioPostCardElegant(
        profileName = "Arjun Beats",
        username = "@arjunbeats",
        profileResId = R.drawable.pro1_img,
        title = "Unleash your full potential. Consistency beats talent every single day! ⚡👑",
        tags = "#motivation #mindset #grind #hustle",
        artworkResId = R.drawable.pro1_img,
        playsCount = "4.1K",
        accentColor = Color(0xFF00B2FE),
        isGlobalPlaying = false,
        currentPosition = 0,
        totalDuration = 240000
    )
}

@Preview(name = "Preview Card 4: MusicLab", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun AudioPostCardMusicLabPreview() {
    AudioPostCardElegant(
        profileName = "MusicLab",
        username = "@musiclab",
        profileResId = R.drawable.music,
        title = "Epic orchestral movements ⚔️🔥",
        tags = "#epic #soundtrack #orchestral #cinematic",
        artworkResId = R.drawable.music,
        playsCount = "892",
        accentColor = Color(0xFFFF5722),
        isGlobalPlaying = false,
        currentPosition = 0,
        totalDuration = 300000
    )
}