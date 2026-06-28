package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.*

@Composable
fun SuggestedForYou() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Suggested For You",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        val suggestions = listOf(
            "ChillVibes", "SadSongs", "FocusMusic", "#motivation",
            "Podcast", "Story Time", "LoFi", "#india"
        )

        // LazyRow enables horizontal scrolling
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(suggestions) { item ->
                SuggestionChip(text = item)
            }
        }
    }
}

@Composable
fun SuggestionChip(text: String) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardBlue)
            .border(1.dp, BorderChip, RoundedCornerShape(12.dp))
            // Removed weight(1f) to let the chip grow with the text content
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.TrendingUp,
            contentDescription = null,
            tint = PrimaryBlue, // Ensure this is defined in your theme
            modifier = Modifier.size(14.dp) // Slightly larger icon for visibility
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            fontSize = 10.sp, // Slightly larger font for better readability
            fontWeight = FontWeight.Medium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Visible // Text will not be clipped
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, widthDp = 390)
@Composable
fun SuggestedForYouPreview() {
    NavHighTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SuggestedForYou()
        }
    }
}