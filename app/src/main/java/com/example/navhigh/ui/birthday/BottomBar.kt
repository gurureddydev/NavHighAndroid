package com.example.navhigh.ui.birthday

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider

// Reuses the same dark palette as BirthdayScreen so the sheet doesn't look
// like a different app when it pops up over it.
private val SheetBg = Color(0xFF03070C)
private val TextWhite = Color(0xFFF2F5F8)
private val TextGrey = Color(0xFFB9C2CC)
private val CyanAccent = Color(0xFF29C4F0)
private val HandleColor = Color(0xFF3A4A63)

/**
 * Bottom sheet explaining why the app asks for a birthday, matching the
 * reference design: drag handle, close (X) icon, bold title, and a body
 * paragraph ending in an inline "Learn more" link.
 */
@Composable
fun BirthdayInfoBottomSheet(
    onDismiss: () -> Unit,
    onLearnMoreClick: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // Same fix as the date wheel picker: strip the Dialog window's own
        // white background drawable so only our dark Surface shows.
        val view = LocalView.current
        SideEffect {
            (view.parent as? DialogWindowProvider)?.window?.setBackgroundDrawable(
                ColorDrawable(android.graphics.Color.TRANSPARENT)
            )
        }

        // Anchor content to the bottom of the screen, full width, like a
        // native bottom sheet — not a centered card.
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SheetBg,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    // Drag handle
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(width = 36.dp, height = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(HandleColor)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = TextWhite,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Birthdays",
                        color = TextWhite,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val bodyText = buildAnnotatedString {
                        append(
                            "Providing your birthday improves the features and ads you see, and helps to keep the community safe. You can find your birthday in your account settings. "
                        )
                        withLink(
                            LinkAnnotation.Clickable(
                                tag = "LEARN_MORE",
                                styles = TextLinkStyles(style = SpanStyle(color = CyanAccent))
                            ) {
                                onLearnMoreClick()
                            }
                        ) {
                            append("Learn more")
                        }
                    }

                    Text(
                        text = bodyText,
                        color = TextGrey,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050B14, widthDp = 393, heightDp = 852)
@Composable
private fun BirthdayInfoBottomSheetPreview() {
    BirthdayInfoBottomSheet(onDismiss = {})
}
