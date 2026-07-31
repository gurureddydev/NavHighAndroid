@file:Suppress("SpellCheckingInspection")

package com.example.navhigh.ui.commentsection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
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
private val QuickReactEmojis = listOf("❤️", "🙌", "🔥", "👏", "😢", "😍", "😮", "😂")

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
            .padding(vertical = 6.dp),
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
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = comment.timeAgo,
                    color = Color(0xFF8E8E8E),
                    fontSize = 10.sp
                )
            }

            Spacer(Modifier.height(2.dp))

            Text(
                text = comment.text,
                color = Color.White,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(1.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Reply",
                    color = Color(0xFF8E8E8E),
                    fontSize = 10.sp,
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
                        fontSize = 10.sp,
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
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = (comment.likeCount + if (isLiked) 1 else 0).toString(),
                color = Color(0xFF8E8E8E),
                fontSize = 9.sp
            )
        }
    }
}

/**
 * Instagram-style comments bottom sheet content. Caller owns the
 * ModalBottomSheet's sheetState (same pattern as PhotoScreen's picker
 * sheet). Header, dividers, emoji row, and input bar are fixed outside the
 * LazyColumn so only the comment list itself scrolls.
 *
 * The header/dividers/emoji row/input bar never move from ordinary
 * scrolling -- including fast flings, and including simply landing on the
 * first comment via momentum. The ONE exception is a deliberate pull:
 * once you're on the first comment and drag down past a small distance
 * (or release with downward fling velocity), [onCloseRequest] is called
 * directly, which plays the sheet's own smooth hide() animation through
 * to full completion. This is a guaranteed full close every time -- it
 * does not depend on ModalBottomSheet's own internal dismiss threshold,
 * which could otherwise decide to stall or bounce back to Expanded
 * partway through. See [scrollConnection] below for exactly how the pull
 * gesture is detected.
 */
@Composable
fun CommentsSheetContent(
    comments: List<Comment>,
    onSendComment: (String) -> Unit,
    modifier: Modifier = Modifier,
    // No default value here on purpose. This closes the sheet -- if some
    // call site forgets to pass it, that used to silently do NOTHING on
    // swipe (an empty default lambda), which is exactly the "swipe does
    // nothing at all" symptom that's hard to debug. Making it required
    // turns a silent runtime no-op into a compile error instead, at every
    // call site including previews.
    onCloseRequest: () -> Unit
) {
    var commentText by remember { mutableStateOf("") }
    var likedIds by remember { mutableStateOf(setOf<Int>()) }

    // fillMaxHeight(0.5f) needs a bounded parent height to size against, but the
    // ModalBottomSheet content slot sizes to content by default -- so we compute
    // a real dp height from the screen instead. This guarantees the sheet always
    // renders at a fixed, visible size, with the comment list scrolling inside it
    // while the emoji row and input bar stay fixed at the bottom.
    val configuration = LocalConfiguration.current
    val sheetHeight = (configuration.screenHeightDp * 0.5f).dp
    val commentsListState = rememberLazyListState()
    val density = androidx.compose.ui.platform.LocalDensity.current

    // Letting the drag/fling bubble up and leaving ModalBottomSheet's own
    // internal threshold decide whether to finish closing or bounce back
    // to Expanded is what caused the "closes halfway then stalls / snaps
    // back" behavior -- that threshold is an internal Material3 decision
    // we don't control, and it doesn't always resolve to "fully closed."
    //
    // To GUARANTEE a full close every time, we stop leaving that decision
    // to the sheet's physics. The moment a real downward pull (past a
    // small distance) or a downward fling is detected while at the top of
    // the list, we call onCloseRequest() directly, which runs
    // sheetState.hide() -- Material3's own smooth slide-to-closed
    // animation, played to full completion. There's no partial/undecided
    // state anymore: once triggered, it always finishes fully closed.
    // IMPORTANT: onCloseRequest (== dismissFully in the caller) is a new
    // lambda instance on every recomposition of the caller. Previously
    // this was used directly as a `remember` key below, which meant ANY
    // unrelated recomposition (typing a character, liking a comment, etc.)
    // silently recreated the whole gesture tracker below and reset its
    // in-progress pull distance / fling-triggered flag back to zero --
    // so a real swipe's progress could get wiped out mid-gesture and
    // never reach the close threshold at all. rememberUpdatedState fixes
    // this: the tracker object stays the SAME stable instance across
    // recompositions (so its accumulated progress survives), while this
    // always exposes the latest onCloseRequest lambda to call.
    val currentOnCloseRequest = rememberUpdatedState(onCloseRequest)

    val scrollConnection = remember(commentsListState) {
        // Raw pixel distance of downward pull accumulated while at the top.
        // Resets whenever the pull sequence breaks (scrolled back up, or
        // not at top).
        var pullAccumulatorPx = 0f
        var closeTriggered = false
        // Kept small and responsive -- this only needs to distinguish "a
        // real deliberate pull" from incidental touch jitter, not act as a
        // strict physics threshold.
        val closeDistanceThresholdPx = with(density) { 28.dp.toPx() }
        val closeFlingVelocityThresholdPxPerSec = with(density) { 400.dp.toPx() }

        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: androidx.compose.ui.input.nestedscroll.NestedScrollSource
            ): Offset {
                val atTop = !commentsListState.canScrollBackward
                val draggingDown = available.y > 0f
                val isActiveDrag = source == androidx.compose.ui.input.nestedscroll.NestedScrollSource.Drag

                if (atTop && draggingDown && isActiveDrag) {
                    pullAccumulatorPx += available.y
                    if (!closeTriggered && pullAccumulatorPx >= closeDistanceThresholdPx) {
                        closeTriggered = true
                        currentOnCloseRequest.value()
                    }
                } else {
                    pullAccumulatorPx = 0f
                    closeTriggered = false
                }

                // Fully consumed either way -- the list/header/footer never
                // visually move from this; closing is driven entirely by
                // onCloseRequest() -> sheetState.hide() above.
                return available
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val atTop = !commentsListState.canScrollBackward
                val flingingDown = available.y > 0f

                if (atTop && flingingDown && !closeTriggered &&
                    available.y >= closeFlingVelocityThresholdPxPerSec
                ) {
                    closeTriggered = true
                    currentOnCloseRequest.value()
                }

                pullAccumulatorPx = 0f
                closeTriggered = false
                return available
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(sheetHeight)
            .nestedScroll(scrollConnection)
    ) {
        HorizontalDivider(color = Color(0xFF2A2A2A), thickness = 0.5.dp)

        // Only this LazyColumn scrolls -- header above and emoji/input bar
        // below are outside of it, so they stay fixed in place.
        LazyColumn(
            state = commentsListState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
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

        HorizontalDivider(color = Color(0xFF2A2A2A), thickness = 0.5.dp)

        // Quick-react emoji row -- fixed, outside the LazyColumn
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
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

        // Input row -- fixed, outside the LazyColumn
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
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
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape24)
                    .background(Color(0xFF1C1C1E))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                if (commentText.isEmpty()) {
                    Text(
                        text = "Join the conversation...",
                        color = Color(0xFF8E8E8E),
                        fontSize = 12.sp
                    )
                }
                androidx.compose.foundation.text.BasicTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 12.sp
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

private val RoundedCornerShape24 =
    androidx.compose.foundation.shape.RoundedCornerShape(24.dp)

/**
 * Call this from wherever you show the comments (e.g. a button's onClick).
 * It owns the ModalBottomSheet + sheetState + open/close state for you.
 *
 * Closing (tap outside, system back, or the pull-down-at-top gesture from
 * CommentsSheetContent) always runs the sheet's own hide() animation to
 * full completion BEFORE removing the composable -- see [dismissFully]
 * below. Wiring onDismissRequest straight to the caller's onDismiss can
 * pull the sheet out of composition mid-animation, which looks like the
 * sheet stopping and cutting off halfway instead of sliding all the way
 * closed.
 *
 * Usage:
 *   var showComments by remember { mutableStateOf(false) }
 *   Button(onClick = { showComments = true }) { Text("Comments") }
 *   CommentsBottomSheet(
 *       show = showComments,
 *       comments = sampleComments(),
 *       onSendComment = { text -> /* add to your comments list */ },
 *       onDismiss = { showComments = false }
 *   )
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    show: Boolean,
    comments: List<Comment>,
    onSendComment: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    // Drives a full, un-interrupted close: animate the sheet's own hide()
    // to completion FIRST, and only call onDismiss() (which removes this
    // composable from composition, via the `if (!show) return` above) once
    // that animation has actually finished. Wiring onDismissRequest
    // straight to onDismiss can pull the composable out mid-animation --
    // that's what was causing the sheet to visually stop and cut off
    // halfway instead of sliding all the way closed.
    val dismissFully: () -> Unit = {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = dismissFully,
        sheetState = sheetState,
        containerColor = LoginBackground,
        // Important: no dragHandle here -- CommentsSheetContent already
        // draws its own "X comments" title as the fixed top row. Adding
        // Material's default drag handle on top would give you two headers.
        dragHandle = null
    ) {
        CommentsSheetContent(
            comments = comments,
            onSendComment = { text ->
                onSendComment(text)
            },
            onCloseRequest = dismissFully
        )
    }
}

// --- PREVIEW ---
// CommentsSheetContent normally renders inside a ModalBottomSheet with
// containerColor = LoginBackground, so the preview wraps it in the same
// background to match how it'll actually look on device.
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
            onSendComment = {},
            onCloseRequest = {} // preview only -- nothing to close here
        )
    }
}
