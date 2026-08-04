@file:Suppress("SpellCheckingInspection")

package com.example.navhigh.ui.commentsection

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.navhigh.R
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- DATA STRUCTURE FOR A SINGLE COMMENT ---
data class Comment(
    val id: Int,
    val username: String,
    val profileResId: Int,
    val timeAgo: String,
    val text: String,
    val likeCount: Int,
    val replyCount: Int = 0
)

// Quick-react emojis shown above the input bar, Instagram-style.
private val QuickReactEmojis = listOf("❤️", "🙌", "🔥", "👏", "😢", "😍", "😮", "😂", "😊", "🙏", "👍", "🎉")

// Spring used to settle the sheet height / pull-to-close offset -- tuned
// to feel like Instagram's own sheet: quick, slightly bouncy, never
// sluggish or abrupt.
private val SheetHeightSpring = spring<Float>(
    dampingRatio = Spring.DampingRatioLowBouncy,
    stiffness = Spring.StiffnessMediumLow
)

// How far (in dp) the user must pull down from the top of the comment
// list before releasing counts as "close the sheet" instead of
// "snap back to normal position".
private val PullToCloseThreshold = 110.dp

// Fling velocity (px/sec) thresholds -- a forceful swipe crosses these
// even if the distance dragged was small, matching Instagram.
private const val ForceExpandVelocity = -900f   // fast swipe UP on the handle
private const val ForceCollapseVelocity = 900f  // fast swipe DOWN on the handle
private const val ForceCloseVelocity = 900f     // fast swipe DOWN on the list -> close

// How long the exit slide takes -- kept in sync with SheetHeightSpring's
// feel, used to time when we tell the caller it's safe to remove this
// composable from composition entirely.
private const val ExitAnimationDurationMs = 250L

// Sample comments so the sheet has something to render immediately.
// Swap this out for your real data source (ViewModel / repository / API).
fun sampleComments(): List<Comment> = listOf(
    Comment(
        id = 1,
        username = "furlan.nicola",
        profileResId = R.drawable.logo,
        timeAgo = "12w",
        text = "It should be delicious! \uD83D\uDC4B",
        likeCount = 3
    ),
    Comment(
        id = 2,
        username = "rajiv_acm",
        profileResId = R.drawable.logo,
        timeAgo = "11w",
        text = "Tamatar daal diye??????.........NAHI DALNE THE \uD83D\uDE02",
        likeCount = 14,
        replyCount = 2
    ),
    Comment(
        id = 3,
        username = "srishailgowda",
        profileResId = R.drawable.logo,
        timeAgo = "7w",
        text = "Oil swimming \uD83D\uDE02\uD83D\uDE02\uD83D\uDE02",
        likeCount = 29
    ),
    Comment(
        id = 4,
        username = "meera.k",
        profileResId = R.drawable.logo,
        timeAgo = "6w",
        text = "This looks amazing, need the recipe!",
        likeCount = 8
    ),
    Comment(
        id = 5,
        username = "arjun_photos",
        profileResId = R.drawable.logo,
        timeAgo = "5w",
        text = "Street food hits different \uD83D\uDD25",
        likeCount = 21
    ),
    Comment(
        id = 6,
        username = "priya.codes",
        profileResId = R.drawable.logo,
        timeAgo = "4w",
        text = "Bro is a chef and a hustler both",
        likeCount = 12
    ),
    Comment(
        id = 7,
        username = "kiran.raj",
        profileResId = R.drawable.logo,
        timeAgo = "3w",
        text = "Where is this located?",
        likeCount = 5,
        replyCount = 1
    ),
    Comment(
        id = 8,
        username = "devi_sharma",
        profileResId = R.drawable.logo,
        timeAgo = "2w",
        text = "The egg game is too strong here \uD83D\uDC4C",
        likeCount = 17
    ),
    Comment(
        id = 9,
        username = "rahul.b",
        profileResId = R.drawable.logo,
        timeAgo = "1w",
        text = "Underrated video, deserves more views",
        likeCount = 9
    ),
    Comment(
        id = 10,
        username = "ananya_v",
        profileResId = R.drawable.logo,
        timeAgo = "5d",
        text = "Saved this for later \uD83D\uDCCC",
        likeCount = 4
    )
)

@Composable
private fun CommentItem(
    comment: Comment,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = comment.profileResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = comment.username,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = comment.timeAgo,
                    color = Color(0xFF8E8E8E),
                    fontSize = 9.sp
                )
            }

            Spacer(Modifier.height(2.dp))

            Text(
                text = comment.text,
                color = Color.White,
                fontSize = 11.sp
            )

            Spacer(Modifier.height(0.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Reply",
                    color = Color(0xFF8E8E8E),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { /* TODO: wire reply flow */ }
                )

                if (comment.replyCount > 0) {
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "View ${comment.replyCount} more replies",
                        color = Color(0xFF8E8E8E),
                        fontSize = 9.sp,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { /* TODO: expand replies */ }
                    )
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 2.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onLikeClick() }
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = if (isLiked) ForgotPasswordBlue else Color(0xFF8E8E8E),
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = (comment.likeCount + if (isLiked) 1 else 0).toString(),
                color = Color(0xFF8E8E8E),
                fontSize = 8.sp
            )
        }
    }
}

/**
 * Instagram-style comments bottom sheet content. Handle bar + "Responses"
 * label + emoji row + input bar are all fixed outside the LazyColumn so
 * only the comment list scrolls.
 */
@Composable
fun CommentsSheetContent(
    comments: List<Comment>,
    onSendComment: (String) -> Unit,
    modifier: Modifier = Modifier,
    onCloseRequest: () -> Unit = {}
) {
    var commentText by remember { mutableStateOf("") }
    var likedIds by remember { mutableStateOf(setOf<Int>()) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val collapsedHeight = (configuration.screenHeightDp * 0.5f).dp
    val expandedHeight = (configuration.screenHeightDp * 0.9f).dp
    val collapsedHeightPx = with(density) { collapsedHeight.toPx() }
    val expandedHeightPx = with(density) { expandedHeight.toPx() }
    val pullToCloseThresholdPx = with(density) { PullToCloseThreshold.toPx() }

    val sheetHeightPx = remember { Animatable(collapsedHeightPx) }
    val dragOffsetPx = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val sheetHeight: Dp = with(density) { sheetHeightPx.value.toDp() }

    val commentsListState = rememberLazyListState()

    val settleHeightToNearest: (flingVelocityY: Float) -> Unit = { flingVelocityY ->
        val settleTo = when {
            flingVelocityY <= ForceExpandVelocity -> expandedHeightPx
            flingVelocityY >= ForceCollapseVelocity -> collapsedHeightPx
            else -> {
                val midPointPx = (collapsedHeightPx + expandedHeightPx) / 2f
                if (sheetHeightPx.value >= midPointPx) expandedHeightPx else collapsedHeightPx
            }
        }
        scope.launch {
            sheetHeightPx.animateTo(
                targetValue = settleTo,
                animationSpec = SheetHeightSpring
            )
        }
    }

    val settlePullToClose: (flingVelocityY: Float) -> Unit = { flingVelocityY ->
        val pulledFarEnough = dragOffsetPx.value > pullToCloseThresholdPx
        val flungFastEnough = flingVelocityY > ForceCloseVelocity
        if (pulledFarEnough || flungFastEnough) {
            scope.launch { dragOffsetPx.snapTo(0f) }
            onCloseRequest()
        } else {
            scope.launch {
                dragOffsetPx.animateTo(
                    targetValue = 0f,
                    animationSpec = SheetHeightSpring
                )
            }
        }
    }

    val pullToCloseConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (dragOffsetPx.value > 0f) {
                    val newOffset = (dragOffsetPx.value + available.y).coerceAtLeast(0f)
                    val consumedY = newOffset - dragOffsetPx.value
                    scope.launch { dragOffsetPx.snapTo(newOffset) }
                    return Offset(0f, consumedY)
                }
                val listAtTop = commentsListState.firstVisibleItemIndex == 0 &&
                        commentsListState.firstVisibleItemScrollOffset == 0
                if (available.y > 0f && listAtTop) {
                    scope.launch { dragOffsetPx.snapTo(dragOffsetPx.value + available.y) }
                    return Offset(0f, available.y)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return available
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (dragOffsetPx.value > 0f) {
                    settlePullToClose(available.y)
                    return available
                }
                return Velocity.Zero
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(sheetHeight)
            .offset { IntOffset(0, dragOffsetPx.value.roundToInt()) }
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(LoginBackground)
            // Absorb taps here so they never fall through to the outer
            // scrim's "tap outside to dismiss" handler.
            .pointerInput(Unit) { detectTapGestures(onTap = {}) }
    ) {
        val handleVelocityTracker = remember { VelocityTracker() }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .pointerInput(collapsedHeightPx, expandedHeightPx) {
                    detectVerticalDragGestures(
                        onDragStart = { handleVelocityTracker.resetTracking() },
                        onVerticalDrag = { change, dragAmount ->
                            change.consume()
                            handleVelocityTracker.addPosition(
                                change.uptimeMillis,
                                change.position
                            )
                            val unclamped = sheetHeightPx.value - dragAmount
                            val clamped = unclamped.coerceIn(collapsedHeightPx, expandedHeightPx)
                            scope.launch { sheetHeightPx.snapTo(clamped) }

                            if (unclamped < collapsedHeightPx) {
                                val excess = collapsedHeightPx - unclamped
                                scope.launch {
                                    dragOffsetPx.snapTo((dragOffsetPx.value + excess).coerceAtLeast(0f))
                                }
                            }
                        },
                        onDragEnd = {
                            val velocityY = handleVelocityTracker.calculateVelocity().y
                            if (dragOffsetPx.value > 0f) {
                                settlePullToClose(velocityY)
                            } else {
                                settleHeightToNearest(velocityY)
                            }
                        },
                        onDragCancel = {
                            if (dragOffsetPx.value > 0f) {
                                settlePullToClose(0f)
                            } else {
                                settleHeightToNearest(0f)
                            }
                        }
                    )
                }
        )

        Text(
            text = "Responses",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        LazyColumn(
            state = commentsListState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .nestedScroll(pullToCloseConnection)
        ) {
            items(comments, key = { it.id }) { comment ->
                CommentItem(
                    comment = comment,
                    isLiked = likedIds.contains(comment.id),
                    onLikeClick = {
                        likedIds = if (likedIds.contains(comment.id)) {
                            likedIds - comment.id
                        } else {
                            likedIds + comment.id
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(androidx.compose.foundation.rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                QuickReactEmojis.forEach { emoji ->
                    Text(
                        text = emoji,
                        fontSize = 20.sp,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { commentText += emoji }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.width(10.dp))

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 40.dp)
                        .clip(RoundedCornerShape24)
                        .background(Color(0xFF1C1C1E))
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    if (commentText.isEmpty()) {
                        Text(
                            text = "Join the conversation...",
                            color = Color(0xFF8E8E8E),
                            fontSize = 11.sp
                        )
                    }
                    androidx.compose.foundation.text.BasicTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = Color.White,
                            fontSize = 11.sp
                        ),
                        singleLine = true,
                        cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.White),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.width(8.dp))

                if (commentText.isNotBlank()) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = ForgotPasswordBlue,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onSendComment(commentText.trim())
                                commentText = ""
                            }
                    )
                }
            }
        }
    }
}

private val RoundedCornerShape24 = RoundedCornerShape(24.dp)

/**
 * Comments sheet rendered as a plain in-window overlay -- NOT a Popup and
 * NOT a Dialog. Both of those create a second Android window, and every
 * time that second window opened/closed, touch/keyboard focus had to be
 * handed back to the Activity's main window -- which is what was
 * intermittently failing and leaving the reels pager (and the sheet
 * itself, on the second open) unresponsive. Staying in one window removes
 * that failure mode entirely: Compose owns all gesture routing itself.
 */
@Composable
fun CommentsBottomSheet(
    show: Boolean,
    comments: List<Comment>,
    onSendComment: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    var visible by remember { mutableStateOf(false) }

    // Hide the system navigation bar while this sheet is open, and restore
    // it the moment the sheet is dismissed.
    val view = LocalView.current
    DisposableEffect(Unit) {
        fun findActivity(context: android.content.Context): android.app.Activity? {
            var ctx = context
            while (ctx is android.content.ContextWrapper) {
                if (ctx is android.app.Activity) return ctx
                ctx = ctx.baseContext
            }
            return ctx as? android.app.Activity
        }

        val activity = findActivity(view.context)
        val insetsController = activity?.window?.let { WindowCompat.getInsetsController(it, view) }
        insetsController?.let { controller ->
            controller.hide(androidx.core.view.WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        onDispose {
            insetsController?.show(androidx.core.view.WindowInsetsCompat.Type.navigationBars())
        }
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    val dismissFully: () -> Unit = {
        visible = false
    }

    LaunchedEffect(visible) {
        if (!visible) {
            delay(ExitAnimationDurationMs)
            onDismiss()
        }
    }

    BackHandler(enabled = show) {
        dismissFully()
    }

    // Transparent full-screen scrim, same window as everything else.
    // Tapping it (outside the sheet content) closes the sheet.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { dismissFully() })
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
        ) {
            CommentsSheetContent(
                comments = comments,
                onSendComment = { text -> onSendComment(text) },
                onCloseRequest = dismissFully
            )
        }
    }
}

// --- PREVIEW ---
@androidx.compose.ui.tooling.preview.Preview(
    name = "Preview: Comments Sheet",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
private fun CommentsSheetContentPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(650.dp)
            .background(LoginBackground)
    ) {
        CommentsSheetContent(
            comments = sampleComments(),
            onSendComment = {}
        )
    }
}