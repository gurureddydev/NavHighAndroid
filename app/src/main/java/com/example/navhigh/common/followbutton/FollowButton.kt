package com.example.navhigh.common.followbutton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.FollowBorderBlue

/**
 * Reusable Follow / Unfollow toggle button.
 * - Border + text color: FollowBorderBlue
 * - Shape: rounded rectangle matching app design
 * - Tapping "Follow" switches to "Following", tapping "Following" switches back to "Follow"
 */
@Composable
fun FollowButton(
    isFollowing: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = FollowBorderBlue
) {
    OutlinedButton(
        onClick = { onToggle(!isFollowing) },
        modifier = modifier
            .height(34.dp)
            .width(100.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, accentColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = accentColor
        )
    ) {
        Text(
            text = if (isFollowing) "Following" else "Follow",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Stateful convenience version: manages its own isFollowing state internally.
 */
@Composable
fun FollowButtonStateful(
    modifier: Modifier = Modifier,
    initiallyFollowing: Boolean = false,
    accentColor: Color = FollowBorderBlue,
    onFollowChanged: (Boolean) -> Unit = {}
) {
    var isFollowing by remember { mutableStateOf(initiallyFollowing) }

    FollowButton(
        isFollowing = isFollowing,
        onToggle = { newValue ->
            isFollowing = newValue
            onFollowChanged(newValue)
        },
        modifier = modifier,
        accentColor = accentColor
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF02070D)
@Composable
fun FollowButtonPreview() {
    FollowButtonStateful()
}
