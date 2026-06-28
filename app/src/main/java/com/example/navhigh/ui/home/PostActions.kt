package com.example.navhigh.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.SecondaryText
import androidx.compose.ui.tooling.preview.PreviewParameter

@Composable
fun PostActions1(
    likes: String = "342",
    comments: String = "28",
    reposts: String = "52"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionItem(Icons.Outlined.FavoriteBorder, likes)
        Spacer(modifier = Modifier.width(36.dp))
        ActionItem(Icons.Outlined.ChatBubbleOutline, comments)
        Spacer(modifier = Modifier.width(36.dp))
        ActionItem(Icons.Outlined.Repeat, reposts)
        Spacer(modifier = Modifier.width(36.dp))
        
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Send,
            contentDescription = "Share",
            tint = SecondaryText,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Icon(
            imageVector = Icons.Outlined.BookmarkBorder,
            contentDescription = "Save",
            tint = SecondaryText,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ActionItem(icon: ImageVector, count: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SecondaryText,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = count,
            color = SecondaryText,
            fontSize = 15.sp
        )
    }
}
@Composable
fun PostActions(
    likes: String = "342",
    comments: String = "28",
    reposts: String = "52"
) {
    // ... your existing implementation
}

// Add this below your existing composables:

@Preview(showBackground = true, widthDp = 400)
@Composable
fun PostActionsPreview() {
    // Wrapping in a Box to provide some padding for the preview
    Box(modifier = Modifier.padding(16.dp)) {
        PostActions()
    }
}
