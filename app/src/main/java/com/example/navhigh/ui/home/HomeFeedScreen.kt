package com.example.navhigh.ui.home


import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.R
import com.example.navhigh.ui.commentsection.Comment
import com.example.navhigh.ui.commentsection.CommentsSheetContent
import com.example.navhigh.ui.commentsection.sampleComments
import com.example.navhigh.ui.theme.LoginBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(onNavigate: (String) -> Unit = {}) {
    val context = LocalContext.current

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabsList = remember { listOf("For You", "Following", "Trending") }

    // --- FOLLOW STATE: lifted up here so it's shared across tabs/cards, starts empty ---
    var followedProfiles by remember { mutableStateOf(setOf<String>()) }

    // --- COMMENTS BOTTOM SHEET STATE (Instagram-style) ---
    // commentsPost holds whichever post's comment icon was tapped -- the sheet
    // shows whenever this is non-null. Comments are cached per post id so
    // switching pages/reopening keeps whatever the user typed/liked.
    var commentsPost by remember { mutableStateOf<HomeFeedAudioPostData?>(null) }
    val commentsSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val commentsByPostId = remember { mutableStateMapOf<String, List<Comment>>() }
    val scope = rememberCoroutineScope()

    val dismissFully: () -> Unit = {
        scope.launch {
            commentsSheetState.hide()
        }.invokeOnCompletion {
            commentsPost = null
        }
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

    // --- REELS PAGER: full-bleed, swipeable, one post per page (For You tab only) ---
    // beyondViewportPageCount = 1 keeps only Previous/Current/Next composed,
    // matching "Only Previous, Current and Next pages are kept in memory" from the guide.
    val pagerState = rememberPagerState(pageCount = { feedList.size })

    // --- SCREEN PLAYER STATE ENGINE ---
    var isAudioPlayingGlobal by remember { mutableStateOf(true) }
    var currentPlaybackPosition by remember { mutableIntStateOf(0) }
    var trackTotalDuration by remember { mutableIntStateOf(120000) }

    // The active post is whichever page the pager is currently on -- swiping
    // IS the navigation now, so there's no separate index/scroll-sync needed.
    val activePost = remember(pagerState.currentPage, selectedTab) {
        if (selectedTab == 0) feedList.getOrNull(pagerState.currentPage) else null
    }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    LaunchedEffect(activePost) {
        if (activePost != null && activePost.audioResId != 0) {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()

                mediaPlayer = MediaPlayer.create(context, activePost.audioResId).apply {
                    trackTotalDuration = duration
                    currentPlaybackPosition = 0
                    seekTo(0)
                    setOnCompletionListener {
                        // Loop the current reel rather than auto-advancing --
                        // advancing is now the user's swipe, matching Reels/TikTok behavior.
                        currentPlaybackPosition = 0
                        seekTo(0)
                        start()
                    }
                }

                isAudioPlayingGlobal = true
                mediaPlayer?.start()
            } catch (e: Exception) {
                Log.e("HomeFeedScreen", "Error updating global audio context", e)
            }
        }
    }

    LaunchedEffect(isAudioPlayingGlobal, mediaPlayer, pagerState.currentPage) {
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

    val togglePlayback: (Boolean) -> Unit = { shouldPlay ->
        isAudioPlayingGlobal = shouldPlay
        try {
            if (shouldPlay) mediaPlayer?.start() else mediaPlayer?.pause()
        } catch (e: Exception) {
            Log.e("HomeFeedScreen", "Error toggling playback", e)
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
        // --- TOP HEADER ---
        TopHeader(
            onSearchClick = { onNavigate("search_route") },
            onNotificationsClick = { onNavigate("notifications_route") }
        )

        FeedTabs(
            tabs = tabsList,
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        // --- FEED AREA: full-bleed VerticalPager, one post per page ---
        // Only rendered for the For You tab -- Following/Trending stay empty,
        // same as your original LazyColumn behavior.
        Box(modifier = Modifier.weight(1f)) {
            if (selectedTab == 0) {
                VerticalPager(
                    state = pagerState,
                    beyondViewportPageCount = 1,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val post = feedList[page]
                    val isActivePage = page == pagerState.currentPage

                    AudioPostCardReel(
                        profileName = post.profileName,
                        username = post.username,
                        timeAgo = post.timeAgo,
                        profileResId = post.profileResId,
                        title = post.title,
                        tags = post.tags,
                        artworkResId = post.artworkResId,
                        playsCount = post.playsCount,
                        initialLikes = post.initialLikes,
                        initialComments = post.initialComments,
                        accentColor = post.accentColor,
                        isGlobalPlaying = isActivePage && isAudioPlayingGlobal,
                        currentPosition = if (isActivePage) currentPlaybackPosition else 0,
                        totalDuration = if (isActivePage) trackTotalDuration else 120000,
                        isFollowing = post.profileName in followedProfiles,
                        onFollowClick = {
                            followedProfiles = followedProfiles + post.profileName
                        },
                        onUnfollowClick = {
                            followedProfiles = followedProfiles - post.profileName
                        },
                        onPlayToggle = { shouldPlay ->
                            if (isActivePage) togglePlayback(shouldPlay)
                        },
                        onSeek = { seekTarget ->
                            if (isActivePage) {
                                currentPlaybackPosition = seekTarget
                                mediaPlayer?.seekTo(seekTarget)
                            }
                        },
                        onProfileClick = {
                            onNavigate("story_route/${post.profileName}")
                        },
                        onCommentClick = {
                            // Seed sample comments the first time this post's sheet is opened.
                            if (commentsByPostId[post.id] == null) {
                                commentsByPostId[post.id] = sampleComments()
                            }
                            commentsPost = post
                            onNavigate("comments_route/${post.id}")
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            // Following (tab 1) and Trending (tab 2) intentionally render nothing,
            // same as your original behavior.
        }
    }

    // --- COMMENTS BOTTOM SHEET OVERLAY ---
    // Instagram-style: sits above the whole feed, dismissible by swipe-down
    // or tapping outside, same pattern as the picker sheet in PhotoScreen.
    commentsPost?.let { post ->
        ModalBottomSheet(
            onDismissRequest = dismissFully,
            sheetState = commentsSheetState,
            containerColor = LoginBackground
        ) {
            CommentsSheetContent(
                comments = commentsByPostId[post.id] ?: emptyList(),
                onSendComment = { newText ->
                    val newComment = Comment(
                        id = (commentsByPostId[post.id]?.maxOfOrNull { it.id } ?: 0) + 1,
                        username = "you",
                        profileResId = post.profileResId,
                        timeAgo = "now",
                        text = newText,
                        likeCount = 0
                    )
                    commentsByPostId[post.id] =
                        (commentsByPostId[post.id] ?: emptyList()) + newComment
                },
                onCloseRequest = dismissFully
            )
        }
    }
}

@Preview(name = "Complete Home Feed Live View", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun HomeFeedScreenPreview() {
    HomeFeedScreen()
}