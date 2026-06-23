
@file:Suppress("SpellCheckingInspection", "UnusedImport")

package com.example.navhigh.ui.home

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val ScreenBackgroundDarkColor = Color(0xFF02070D)
private val TopHeaderAccentBlueColor = Color(0xFF00B2FE)
private val PrimaryBlue = Color(0xFF00B2FE)

data class LocalStoryData(val id: Int, val name: String, val image: Int, val isLive: Boolean = false)

data class HomeFeedAudioPostData(
    val id: String,
    val profileName: String,
    val username: String,
    val timeAgo: String,
    val title: String,
    val tags: String,
    val profileResId: Int,
    val artworkResId: Int,
    val audioResId: Int,
    val accentColor: Color,
    val playsCount: String,
    val initialLikes: Int = 1250,
    val initialComments: Int = 84
)

@Composable
fun HomeFeedScreen(onNavigate: (String) -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabsList = remember { listOf("For You", "Following", "Trending") }
    val scrollState = rememberLazyListState()

    val stories = remember {
        listOf(
            LocalStoryData(1, "Your Story", R.drawable.robo),
            LocalStoryData(2, "Arjun Beats", R.drawable.pro1_img, isLive = true),
            LocalStoryData(3, "Ivana Voice", R.drawable.ivana),
            LocalStoryData(4, "MusicLab", R.drawable.music),
            LocalStoryData(5, "EchoFlow", R.drawable.logo),
            LocalStoryData(6, "See All", R.drawable.img)
        )
    }

    val feedList = remember {
        listOf(
            HomeFeedAudioPostData(
                id = "post_ivana",
                profileName = "Ivana Voice",
                username = "@ivanavoice",
                timeAgo = "1h ago",
                title = "Testing out the new high-fidelity microphone setup. Sounds incredibly clean! 🎙️🎧",
                tags = "#podcast #audio #engineering #miccheck",
                profileResId = R.drawable.ivana,
                artworkResId = R.drawable.ivana,
                audioResId = R.raw.jack,
                accentColor = Color(0xFFFFC107),
                playsCount = "1.2K"
            ),
            HomeFeedAudioPostData(
                id = "post_echoflow",
                profileName = "EchoFlow",
                username = "@echoflow",
                timeAgo = "1h ago",
                title = "Stop making excuses. Your future self is watching you. Wake up and grind! 🔥💪",
                tags = "#motivation #mindset #discipline #success",
                profileResId = R.drawable.logo,
                artworkResId = R.drawable.logo,
                audioResId = R.raw.motivation,
                accentColor = Color(0xFF00FFCC),
                playsCount = "12.5K"
            ),
            HomeFeedAudioPostData(
                id = "post_arjun",
                profileName = "Arjun Beats",
                username = "@arjunbeats",
                timeAgo = "2h ago",
                title = "Unleash your full potential. Consistency beats talent every single day! ⚡👑",
                tags = "#motivation #mindset #grind #hustle",
                profileResId = R.drawable.pro1_img,
                artworkResId = R.drawable.pro1_img,
                audioResId = R.raw.got,
                accentColor = Color(0xFF00B2FE),
                playsCount = "4.1K"
            ),
            HomeFeedAudioPostData(
                id = "post_musiclab",
                profileName = "MusicLab",
                username = "@musiclab",
                timeAgo = "5h ago",
                title = "Epic orchestral movements ⚔️🔥",
                tags = "#epic #soundtrack #orchestral #cinematic",
                profileResId = R.drawable.music,
                artworkResId = R.drawable.music,
                audioResId = R.raw.aa23,
                accentColor = Color(0xFFFF5722),
                playsCount = "892"
            )
        )
    }

    // --- SCREEN PLAYER STATE ENGINE ---
    var currentPlayingIndex by remember { mutableStateOf<Int?>(0) }
    var isAudioPlayingGlobal by remember { mutableStateOf(true) }
    var currentPlaybackPosition by remember { mutableIntStateOf(0) }
    var trackTotalDuration by remember { mutableIntStateOf(120000) }

    val activePost = remember(currentPlayingIndex) {
        currentPlayingIndex?.let { index -> if (index in feedList.indices) feedList[index] else null }
    }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    LaunchedEffect(activePost) {
        if (activePost != null && activePost.audioResId != 0) {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()

                mediaPlayer = MediaPlayer.create(context, activePost.audioResId).apply {
                    trackTotalDuration = duration
                    seekTo(currentPlaybackPosition.coerceAtMost(duration))
                    setOnCompletionListener {
                        val nextIndex = (currentPlayingIndex ?: 0) + 1
                        if (nextIndex < feedList.size) {
                            currentPlaybackPosition = 0
                            currentPlayingIndex = nextIndex
                            isAudioPlayingGlobal = true
                        } else {
                            isAudioPlayingGlobal = false
                            currentPlaybackPosition = 0
                        }
                    }
                }

                if (isAudioPlayingGlobal) {
                    mediaPlayer?.start()
                }
            } catch (e: Exception) {
                Log.e("HomeFeedScreen", "Error updating global audio context", e)
            }
        }
    }

    LaunchedEffect(currentPlayingIndex) {
        currentPlayingIndex?.let { index ->
            scope.launch {
                scrollState.animateScrollToItem(index + 1)
            }
        }
    }

    LaunchedEffect(isAudioPlayingGlobal, mediaPlayer, currentPlayingIndex) {
        if (isAudioPlayingGlobal && mediaPlayer != null) {
            while (isAudioPlayingGlobal) {
                try {
                    if (mediaPlayer?.isPlaying == true) {
                        currentPlaybackPosition = mediaPlayer?.currentPosition ?: 0
                    }
                } catch (e: Exception) {
                    Log.e("HomeFeedScreen", "Playback polling error occurred", e)
                    break
                }
                delay(30)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            } catch (e: Exception) {
                Log.e("HomeFeedScreen", "Error disposing driver references", e)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackgroundDarkColor)
    ) {
        // --- BRAND-NEW TOP HEADER AT THE TOP ---
        TopHeader(
            onSearchClick = { onNavigate("search_route") },
            onNotificationsClick = { onNavigate("notifications_route") }
        )

        FeedTabs(
            tabs = tabsList,
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp, start = 14.dp, end = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 2.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = stories, key = { it.id }) { story ->
                        StoryItem1(
                            image = story.image,
                            name = story.name,
                            isLive = story.isLive,
                            onClick = {
                                onNavigate("story_route/${story.name}")
                            }
                        )
                    }
                }
            }

            itemsIndexed(feedList, key = { _, post -> post.id }) { index, post ->
                val isActiveCard = currentPlayingIndex == index
                val isThisCardPlaying = isActiveCard && isAudioPlayingGlobal

                AudioPostCardElegant(
                    profileName = post.profileName,
                    username = post.username,
                    timeAgo = post.timeAgo,
                    title = post.title,
                    tags = post.tags,
                    profileResId = post.profileResId,
                    artworkResId = post.artworkResId,
                    playsCount = post.playsCount,
                    initialLikes = post.initialLikes,
                    initialComments = post.initialComments,
                    accentColor = post.accentColor,
                    isGlobalPlaying = isThisCardPlaying,
                    currentPosition = if (isActiveCard) currentPlaybackPosition else 0,
                    totalDuration = if (isActiveCard) trackTotalDuration else 120000,
                    onProfileClick = {
                        onNavigate("story_route/${post.profileName}")
                    },
                    onPlayToggle = { shouldPlay ->
                        if (isActiveCard) {
                            isAudioPlayingGlobal = shouldPlay
                            if (shouldPlay) mediaPlayer?.start() else mediaPlayer?.pause()
                        } else {
                            currentPlaybackPosition = 0
                            currentPlayingIndex = index
                            isAudioPlayingGlobal = true
                        }
                    },
                    onSeek = { seekTarget ->
                        if (isActiveCard) {
                            currentPlaybackPosition = seekTarget
                            mediaPlayer?.seekTo(seekTarget)
                        }
                    }
                )
            }
        }
    }
}

// ==========================================
// TOP HEADER LAYOUT
// ==========================================
@Composable
fun TopHeader(
    onSearchClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.nav),
                contentDescription = "NavHigh Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = "Nav",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "High",
                color = PrimaryBlue,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }

        IconButton(onClick = onNotificationsClick) {
            BadgedBox(
                badge = {
                    Badge(containerColor = PrimaryBlue) {
                        Text("3", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun StoryItem1(image: Int, name: String, isLive: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(68.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = if (isLive) Color(0xFFFF0055) else TopHeaderAccentBlueColor,
                        shape = CircleShape
                    )
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

            if (name == "Your Story") {
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Add Story",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(20.dp)
                        .background(ScreenBackgroundDarkColor, CircleShape)
                        .padding(1.dp)
                        .clip(CircleShape)
                )
            } else if (isLive) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFF0055), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = "LIVE",
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
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

@Composable
fun FeedTabs(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        tabs.forEachIndexed { index, tabName ->
            val isSelected = index == selectedTabIndex
            Column(
                modifier = Modifier
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 14.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tabName,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(44.dp)
                        .background(if (isSelected) TopHeaderAccentBlueColor else Color.Transparent, RoundedCornerShape(1.dp))
                )
            }
        }
    }
}

@Preview(name = "Complete Home Feed Live View", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun HomeFeedScreenPreview() {
    HomeFeedScreen()
}

