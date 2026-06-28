package com.example.navhigh.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.*

@Composable
fun RecentSearches(
    items: List<String>,
    onClearAll: () -> Unit,
    onRemoveItem: (String) -> Unit
) {
    // If the list is empty, the entire component returns early and remains invisible
    if (items.isEmpty()) return

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Searches",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                text = "Clear All",
                fontSize = 14.sp,
                color = PrimaryBlue,
                modifier = Modifier.clickable { onClearAll() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { item ->
                RecentSearchChip(
                    text = item,
                    onRemove = { onRemoveItem(item) }
                )
            }
        }
    }
}

@Composable
fun RecentSearchChip(
    text: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CardBlue)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = SecondaryText,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Spacer(modifier = Modifier.width(5.dp))

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            tint = SecondaryText,
            modifier = Modifier
                .size(16.dp)
                .clickable { onRemove() }
        )
    }
}

@Preview(
    name = "Recent Searches",
    showBackground = true,
    backgroundColor = 0xFF121212,
    widthDp = 390
)
@Composable
fun RecentSearchesPreview() {
    val sampleItems = remember { mutableStateListOf("Lofi Beats", "Motivation", "#podcast", "Arjun Beats") }

    NavHighTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(20.dp)
        ) {
            RecentSearches(
                items = sampleItems,
                onClearAll = { sampleItems.clear() },
                onRemoveItem = { item -> sampleItems.remove(item) }
            )
        }
    }
}