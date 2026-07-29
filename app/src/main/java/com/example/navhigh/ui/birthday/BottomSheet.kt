package com.example.navhigh.ui.birthday

import android.graphics.Color as AndroidColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.graphics.drawable.toDrawable
import com.example.navhigh.R
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.BirthdaySheetAnimationDurationMs
import com.example.navhigh.ui.theme.BirthdaySheetBg
import com.example.navhigh.ui.theme.BirthdaySheetCyanAccent
import com.example.navhigh.ui.theme.BirthdaySheetDimAmount
import com.example.navhigh.ui.theme.BirthdaySheetDragMinOffset
import com.example.navhigh.ui.theme.BirthdaySheetHandleColor
import com.example.navhigh.ui.theme.BirthdaySheetSpringBackDelayMs
import com.example.navhigh.ui.theme.BirthdaySheetSpringBackSteps
import com.example.navhigh.ui.theme.BirthdaySheetTextGrey
import com.example.navhigh.ui.theme.BirthdaySheetTextWhite
import com.example.navhigh.ui.theme.FullWeight
import kotlinx.coroutines.launch

@Composable
fun BirthdayInfoBottomSheet(
    onDismiss: () -> Unit,
    onLearnMoreClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val visibleState = remember { MutableTransitionState(false).apply { targetState = true } }

    // Only call onDismiss once the exit animation has actually finished —
    // isIdle is true when currentState has caught up with targetState.
    LaunchedEffect(visibleState.currentState, visibleState.targetState, visibleState.isIdle) {
        if (visibleState.isIdle && !visibleState.currentState && !visibleState.targetState) {
            onDismiss()
        }
    }

    // Extra manual drag offset layered on top of the enter/exit animation,
    // used only while the sheet is fully open and being dragged by the user.
    var dragOffset by remember { mutableFloatStateOf(0f) }

    Dialog(
        onDismissRequest = { visibleState.targetState = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val view = LocalView.current
        SideEffect {
            (view.parent as? DialogWindowProvider)?.window?.let { window ->
                window.setBackgroundDrawable(AndroidColor.TRANSPARENT.toDrawable())
                window.setDimAmount(BirthdaySheetDimAmount)
            }
        }

        val windowInfo = LocalWindowInfo.current
        val screenHeight = with(density) { windowInfo.containerSize.height.toDp() }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedVisibility(
                visibleState = visibleState,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(BirthdaySheetAnimationDurationMs)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(BirthdaySheetAnimationDurationMs)
                )
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * AppDimensions.BirthdaySheetHeightFraction)
                        .graphicsLayer { translationY = dragOffset },
                    color = BirthdaySheetBg,
                    shape = RoundedCornerShape(
                        topStart = AppDimensions.BirthdaySheetCornerRadius,
                        topEnd = AppDimensions.BirthdaySheetCornerRadius
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .pointerInput(Unit) {
                                detectVerticalDragGestures(
                                    onVerticalDrag = { change, dragAmount ->
                                        change.consume()
                                        dragOffset = (dragOffset + dragAmount).coerceAtLeast(
                                            BirthdaySheetDragMinOffset
                                        )
                                    },
                                    onDragEnd = {
                                        val thresholdPx =
                                            with(density) { AppDimensions.BirthdaySheetDragDismissThreshold.toPx() }
                                        if (dragOffset > thresholdPx) {
                                            visibleState.targetState = false
                                        } else {
                                            coroutineScope.launch {
                                                // Spring the drag offset back to 0.
                                                val start = dragOffset
                                                val steps = BirthdaySheetSpringBackSteps
                                                for (i in steps downTo 1) {
                                                    dragOffset = start * i / steps
                                                    kotlinx.coroutines.delay(BirthdaySheetSpringBackDelayMs)
                                                }
                                                dragOffset = 0f
                                            }
                                        }
                                    }
                                )
                            }
                            .padding(
                                horizontal = AppDimensions.BirthdaySheetHorizontalPadding,
                                vertical = AppDimensions.BirthdaySheetVerticalPadding
                            )
                    ) {
                        // Drag Handle
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .width(AppDimensions.BirthdaySheetHandleWidth)
                                .height(AppDimensions.BirthdaySheetHandleHeight)
                                .clip(RoundedCornerShape(AppDimensions.BirthdaySheetHandleCornerRadius))
                                .background(BirthdaySheetHandleColor)
                        )

                        Spacer(modifier = Modifier.height(AppDimensions.BirthdaySheetHandleSpacing))

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.birthday_sheet_close_description),
                            tint = BirthdaySheetTextWhite,
                            modifier = Modifier
                                .size(AppDimensions.BirthdaySheetCloseIconSize)
                                .clickable { visibleState.targetState = false }
                        )

                        Spacer(modifier = Modifier.height(AppDimensions.BirthdaySheetCloseIconSpacing))

                        Text(
                            text = stringResource(R.string.birthday_sheet_title),
                            color = BirthdaySheetTextWhite,
                            fontSize = AppTypography.BirthdaySheetTitleTextSize,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(AppDimensions.BirthdaySheetTitleSpacing))

                        val learnMoreText = stringResource(R.string.birthday_sheet_learn_more)
                        val body = buildAnnotatedString {
                            append(stringResource(R.string.birthday_sheet_body))
                            withLink(
                                LinkAnnotation.Clickable(
                                    tag = "learn",
                                    styles = TextLinkStyles(style = SpanStyle(color = BirthdaySheetCyanAccent))
                                ) {
                                    onLearnMoreClick()
                                }
                            ) {
                                append(learnMoreText)
                            }
                        }

                        Text(
                            text = body,
                            color = BirthdaySheetTextGrey,
                            fontSize = AppTypography.BirthdaySheetBodyTextSize,
                            lineHeight = AppTypography.BirthdaySheetBodyLineHeight
                        )

                        Spacer(modifier = Modifier.weight(FullWeight))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852, name = "Phone")
@Composable
private fun BirthdayInfoBottomSheetPhonePreview() {
    BirthdayInfoBottomSheet(onDismiss = {}, onLearnMoreClick = {})
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1280, name = "Tablet")
@Composable
private fun BirthdayInfoBottomSheetTabletPreview() {
    BirthdayInfoBottomSheet(onDismiss = {}, onLearnMoreClick = {})
}