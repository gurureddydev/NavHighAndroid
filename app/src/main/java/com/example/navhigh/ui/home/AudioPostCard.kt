@file:Suppress("SpellCheckingInspection")

package com.example.navhigh.ui.home

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.commentsection.Comment
import com.example.navhigh.ui.commentsection.CommentsBottomSheet
import com.example.navhigh.ui.commentsection.sampleComments
import com.example.navhigh.ui.theme.BirthdayBgWhite
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import kotlinx.coroutines.delay


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

// =====================================================================================
// REELS-STYLE HOME SCREEN (this is the screen you should show for your Home tab)
// Fixed header + full-screen swipeable VerticalPager feed + fixed bottom nav.
// Only the current page plays; others are paused. Swipe up = next, swipe down = previous.
// =====================================================================================
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReelsHomeScreen(
    tracks: List<AudioTrackItem>,
    modifier: Modifier = Modifier,
    onProfileClick: (AudioTrackItem) -> Unit = {},
    onCommentClick: (AudioTrackItem) -> Unit = {}
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { tracks.size })

    var isPlaying by remember { mutableStateOf(true) }
    var currentPlaybackPosition by remember { mutableIntStateOf(0) }
    var trackTotalDuration by remember { mutableIntStateOf(120_000) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var followedIds by remember { mutableStateOf(setOf<Int>()) }

    // --- Comments bottom sheet state (Instagram-style), hoisted here so a
    // single sheet overlays the whole pager instead of living inside each card.
    // CommentsBottomSheet is a plain in-window overlay (no Popup/Dialog) --
    // which is exactly why it now needs to be a sibling of the pager inside
    // ONE shared Box below (see the wrapping Box in the layout section):
    // without that shared Box, the pager and the sheet would just be laid
    // out one after another by whatever container calls ReelsHomeScreen,
    // instead of stacking on top of each other. ---
    var commentsTrack by remember { mutableStateOf<AudioTrackItem?>(null) }
    val commentsByTrackId = remember { mutableStateMapOf<Int, List<Comment>>() }

    val activeTrack = remember(pagerState.currentPage, tracks) {
        tracks.getOrNull(pagerState.currentPage)
    }

    // Load + play whichever track is currently centered in the pager.
    // Always stop/release whatever was playing BEFORE deciding whether the
    // new page even has audio -- otherwise scrolling from a track with
    // audio to one without leaves the old track playing forever, since the
    // stop/release call used to live inside the "has audio" check.
    LaunchedEffect(activeTrack) {
        try {
            mediaPlayer?.stop()
        } catch (e: Exception) {
            // Can throw if the player was never prepared/started -- safe to ignore.
        }
        mediaPlayer?.release()
        mediaPlayer = null
        currentPlaybackPosition = 0

        if (activeTrack != null && activeTrack.audioResId != 0) {
            try {
                mediaPlayer = MediaPlayer.create(context, activeTrack.audioResId).apply {
                    trackTotalDuration = duration
                    seekTo(0)
                    setOnCompletionListener {
                        currentPlaybackPosition = 0
                        seekTo(0)
                        start() // loop the current reel
                    }
                }
                isPlaying = true
                mediaPlayer?.start()
            } catch (e: Exception) {
                Log.e("ReelsHomeScreen", "Error initializing playback", e)
            }
        } else {
            trackTotalDuration = 120_000
        }
    }

    // Poll playback position for the progress line while playing.
    LaunchedEffect(isPlaying, mediaPlayer, pagerState.currentPage) {
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

    val togglePlayback: (Boolean) -> Unit = { playState ->
        isPlaying = playState
        try {
            if (playState) mediaPlayer?.start() else mediaPlayer?.pause()
        } catch (e: Exception) {
            Log.e("ReelsHomeScreen", "Error toggling playback", e)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            } catch (e: Exception) {
                Log.e("ReelsHomeScreen", "Error releasing player", e)
            }
        }
    }

    // --- Pager and comments sheet MUST share one Box so the sheet overlays
    // the reel instead of pushing/squeezing it out of the layout. ---
    Box(modifier = modifier.fillMaxSize()) {
        // --- FEED AREA: full-bleed VerticalPager, one post per page ---
        // userScrollEnabled is explicitly false while the comments sheet is
        // open, so the pager can never contend with the sheet for a drag
        // gesture -- it's fully, officially disabled at the pager level.
        VerticalPager(
            state = pagerState,
            beyondViewportPageCount = 1,
            userScrollEnabled = commentsTrack == null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF02070D))
        ) { page ->
            val track = tracks[page]
            val isActive = page == pagerState.currentPage

            AudioPostCardReel(
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
                totalDuration = if (isActive) trackTotalDuration else 120_000,
                isFollowing = followedIds.contains(track.id),
                onFollowClick = { followedIds = followedIds + track.id },
                onUnfollowClick = { followedIds = followedIds - track.id },
                onPlayToggle = { playRequested: Boolean ->
                    if (isActive) togglePlayback(playRequested)
                },
                onSeek = { seekTarget: Int ->
                    if (isActive) {
                        currentPlaybackPosition = seekTarget
                        mediaPlayer?.seekTo(seekTarget)
                    }
                },
                onProfileClick = { onProfileClick(track) },
                onCommentClick = {
                    if (commentsByTrackId[track.id] == null) {
                        commentsByTrackId[track.id] = sampleComments()
                    }
                    commentsTrack = track
                    onCommentClick(track)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // --- Comments bottom sheet overlay -- sits ON TOP of the pager
        // above, inside the same Box, same window. ---
        val currentCommentsTrack = commentsTrack
        CommentsBottomSheet(
            show = currentCommentsTrack != null,
            comments = currentCommentsTrack?.let { commentsByTrackId[it.id] } ?: emptyList(),
            onSendComment = { newText ->
                val track = currentCommentsTrack ?: return@CommentsBottomSheet
                val newComment = Comment(
                    id = (commentsByTrackId[track.id]?.maxOfOrNull { c: Comment -> c.id } ?: 0) + 1,
                    username = "you",
                    profileResId = track.profileResId,
                    timeAgo = "now",
                    text = newText,
                    likeCount = 0
                )
                commentsByTrackId[track.id] =
                    (commentsByTrackId[track.id] ?: emptyList<Comment>()) + newComment
            },
            onDismiss = { commentsTrack = null }
        )
    }
}

// --- FULL-SCREEN REELS-STYLE CARD (used inside VerticalPager on Home) ---
// Media is TRUE full-bleed: the ironman.png artwork image fills the entire
// card, and everything else (profile row, caption, hashtags, action rail)
// floats on top of it over a bottom gradient scrim -- matching the NavHigh
// design.
@Composable
fun AudioPostCardReel(
    profileName: String,
    username: String,
    timeAgo: String,
    profileResId: Int,
    title: String,
    tags: String,
    artworkResId: Int,
    playsCount: String,
    initialLikes: Int = 1250,
    initialComments: Int = 84,
    accentColor: Color,
    isGlobalPlaying: Boolean,
    currentPosition: Int,
    totalDuration: Int,
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    onUnfollowClick: () -> Unit,
    onPlayToggle: (Boolean) -> Unit,
    onSeek: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onCommentClick: () -> Unit,
    extraBottomPadding: Dp = 0.dp,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(initialLikes) }
    var isSaved by remember { mutableStateOf(false) }
    var savedCount by remember { mutableIntStateOf(181) }
    var sendCount by remember { mutableIntStateOf(110) }

    var showDoubleTapHeart by remember { mutableStateOf(false) }
    var doubleTapOffset by remember { mutableStateOf(Offset.Zero) }
    val density = androidx.compose.ui.platform.LocalDensity.current
    val heartSizePx = with(density) { 100.dp.toPx() }

    LaunchedEffect(showDoubleTapHeart) {
        if (showDoubleTapHeart) {
            delay(650)
            showDoubleTapHeart = false
        }
    }

    val latestIsGlobalPlaying by rememberUpdatedState(isGlobalPlaying)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF02070D))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onPlayToggle(!latestIsGlobalPlaying)
                    },
                    onDoubleTap = { tapOffset ->
                        if (!isLiked) {
                            isLiked = true
                            likesCount++
                        }
                        doubleTapOffset = tapOffset
                        showDoubleTapHeart = true
                    }
                )
            }
    ) {
        // --- Full-bleed background artwork image (ironman.png from res/drawable) ---
        Image(
            painter = painterResource(id = R.drawable.ironman),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Bottom gradient scrim so the overlaid text/icons stay readable.
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                    )
                )
        )

        // Center play/pause glyph inside a translucent circle, fades in only while paused.
        val playIconAlpha by animateFloatAsState(
            targetValue = if (isGlobalPlaying) 0f else 1f,
            label = "playIconAlpha"
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-90).dp)
                .size(64.dp)
                .alpha(playIconAlpha)
                .background(Color.Black.copy(alpha = 0.35f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isGlobalPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        // --- Big heart that pops in right where the user double-tapped ---
        AnimatedVisibility(
            visible = showDoubleTapHeart,
            enter = scaleIn(initialScale = 0.6f) + fadeIn(),
            exit = scaleOut(targetScale = 1.2f) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset {
                    androidx.compose.ui.unit.IntOffset(
                        (doubleTapOffset.x - heartSizePx / 2f).toInt(),
                        (doubleTapOffset.y - heartSizePx / 2f).toInt()
                    )
                }
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = ForgotPasswordBlue,
                modifier = Modifier.size(100.dp)
            )
        }

        // --- Right-side floating action rail ---
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 14.dp, bottom = 48.dp + extraBottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReelAction(
                icon = if (isLiked) Icons.Default.Favorite else null,
                iconRes = if (isLiked) null else R.drawable.heart,
                tint = if (isLiked) ForgotPasswordBlue else BirthdayBgWhite,
                count = likesCount
            ) {
                isLiked = !isLiked
                if (isLiked) likesCount++ else likesCount--
            }
            ReelAction(
                iconRes = R.drawable.comment,
                tint = Color.White,
                count = initialComments
            ) { onCommentClick() }
            ReelAction(
                iconRes = R.drawable.share,
                tint = Color(0xFFD9D9D9),
                count = sendCount
            ) { sendCount++ }
            ReelAction(
                iconRes = R.drawable.save,
                tint = Color(0xFFD9D9D9),
                count = savedCount
            ) {
                isSaved = !isSaved
                if (isSaved) savedCount++ else savedCount--
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.earphones),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color(0xFFD9D9D9)),
                    modifier = Modifier.size(26.dp)
                )
                Spacer(Modifier.height(2.dp))
                Text(playsCount, color = Color.White, fontSize = 11.sp)
            }
        }

        // --- Bottom-left overlay: avatar, username, follow, caption, hashtags ---
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, end = 80.dp, bottom = 30.dp + extraBottomPadding)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = profileResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable { onProfileClick() }
                )
                Spacer(Modifier.width(8.dp))
                Text(profileName, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(4.dp))
                Icon(Icons.Default.CheckCircle, null, tint = accentColor, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(10.dp))
                if (!isFollowing) {
                    OutlinedButton(
                        onClick = onFollowClick,
                        modifier = Modifier.height(28.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                        shape = RoundedCornerShape(50),
                        border = borderStroke(1.dp, ForgotPasswordBlue),
                    ) {
                        Text("Follow", color = ForgotPasswordBlue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                } else {
                    Button(
                        onClick = onUnfollowClick,
                        modifier = Modifier.height(28.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = ForgotPasswordBlue),
                    ) {
                        Text("Following", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(title, color = Color.White, fontSize = 12.sp, maxLines = 1
                ,overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(2.dp))
            Text(tags, color = accentColor, fontSize = 10.sp)
        }
    }
}

@Composable
private fun ReelAction(
    icon: ImageVector? = null,
    tint: Color,
    count: Int?,
    iconRes: Int? = null,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }
    ) {
        if (iconRes != null) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(tint),
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(icon!!, null, tint = tint, modifier = Modifier.size(24.dp))
        }
        if (count != null) {
            Spacer(Modifier.height(0.dp))
            Text(formatReelCount(count), color = Color.White, fontSize = 10.sp)
        }
    }
}

private fun formatReelCount(count: Int): String =
    if (count >= 1000) "%.1fK".format(count / 1000f) else count.toString()

private fun borderStroke(width: Dp, color: Color) = androidx.compose.foundation.BorderStroke(width, color)

// Sample data so ReelsHomeScreen has something to render immediately.
// Swap this out for your real data source (ViewModel / repository / API).
// NOTE: audioResId is 0 for all three below -- that means NO audio will
// play for any of these, since ReelsHomeScreen deliberately skips loading
// a MediaPlayer when audioResId == 0. Replace the 0s with your real
// R.raw.* audio resource ids (like you used in AudioPostCard.kt) for
// playback to actually happen when scrolling.
fun sampleAudioTracks(): List<AudioTrackItem> = listOf(
    AudioTrackItem(
        id = 2,
        profileName = "EchoFlow",
        username = "@echoflow",
        timeAgo = "3h ago",
        profileResId = R.drawable.logo,
        title = "Stop making excuses. Your future self is watching you. Wake up and grind! 🔥💪",
        tags = "#motivation #mindset #discipline #success",
        artworkResId = R.drawable.ironman,
        audioResId = 0, // TODO: replace with R.raw.<your_track>
        playsCount = "12.5K",
        accentColor = Color(0xFF00FFCC)
    ),
    AudioTrackItem(
        id = 3,
        profileName = "Arjun Beats",
        username = "@arjunbeats",
        timeAgo = "5h ago",
        profileResId = R.drawable.pro1_img,
        title = "Unleash your full potential. Consistency beats talent every single day! ⚡👑",
        tags = "#motivation #mindset #grind #hustle",
        artworkResId = R.drawable.pro1_img,
        audioResId = 0, // TODO: replace with R.raw.<your_track>
        playsCount = "4.1K",
        accentColor = Color(0xFF00B2FE)
    ),
    AudioTrackItem(
        id = 4,
        profileName = "MusicLab",
        username = "@musiclab",
        timeAgo = "1d ago",
        profileResId = R.drawable.music,
        title = "Epic orchestral movements ⚔️🔥",
        tags = "#epic #soundtrack #orchestral #cinematic",
        artworkResId = R.drawable.music,
        audioResId = 0, // TODO: replace with R.raw.<your_track>
        playsCount = "892",
        accentColor = Color(0xFFFF5722)
    )
)

// --- PREVIEWS: all four reels, plus the full scrollable feed ---

@Preview(name = "Preview: Full Reels Home Feed (all 4 posts, scrollable)", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun ReelsHomeScreenPreview() {
    ReelsHomeScreen(
        tracks = sampleAudioTracks()
    )
}

@Preview(name = "Preview: Reels Full Screen - EchoFlow", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun AudioPostCardReelEchoFlowPreview() {
    AudioPostCardReel(
        profileName = "EchoFlow",
        username = "@echoflow",
        timeAgo = "3h ago",
        profileResId = R.drawable.logo,
        title = "Stop making excuses. Your future self is watching you. Wake up and grind! 🔥💪",
        tags = "#motivation #mindset #discipline #success",
        artworkResId = R.drawable.ironman,
        playsCount = "12.5K",
        accentColor = Color(0xFF00FFCC),
        isGlobalPlaying = false,
        currentPosition = 0,
        totalDuration = 60000,
        isFollowing = false,
        onFollowClick = {},
        onUnfollowClick = {},
        onPlayToggle = {},
        onSeek = {},
        onProfileClick = {},
        onCommentClick = {},
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(name = "Preview: Reels Full Screen - Arjun Beats", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun AudioPostCardReelArjunPreview() {
    AudioPostCardReel(
        profileName = "Arjun Beats",
        username = "@arjunbeats",
        timeAgo = "1h ago",
        profileResId = R.drawable.pro1_img,
        title = "Unleash your full potential. Consistency beats talent every single day! ⚡👑",
        tags = "#motivation #mindset #grind #hustle",
        artworkResId = R.drawable.pro1_img,
        playsCount = "4.1K",
        accentColor = Color(0xFF00B2FE),
        isGlobalPlaying = true,
        currentPosition = 42000,
        totalDuration = 240000,
        isFollowing = false,
        onFollowClick = {},
        onUnfollowClick = {},
        onPlayToggle = {},
        onSeek = {},
        onProfileClick = {},
        onCommentClick = {},
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(name = "Preview: Reels Full Screen - MusicLab", showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun AudioPostCardReelMusicLabPreview() {
    AudioPostCardReel(
        profileName = "MusicLab",
        username = "@musiclab",
        timeAgo = "1d ago",
        profileResId = R.drawable.music,
        title = "Epic orchestral movements ⚔️🔥",
        tags = "#epic #soundtrack #orchestral #cinematic",
        artworkResId = R.drawable.music,
        playsCount = "892",
        accentColor = Color(0xFFFF5722),
        isGlobalPlaying = false,
        currentPosition = 0,
        totalDuration = 300000,
        isFollowing = false,
        onFollowClick = {},
        onUnfollowClick = {},
        onPlayToggle = {},
        onSeek = {},
        onProfileClick = {},
        onCommentClick = {},
        modifier = Modifier.fillMaxSize()
    )
}