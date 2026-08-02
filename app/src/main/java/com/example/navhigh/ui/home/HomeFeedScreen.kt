package com.example.navhigh.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun HomeFeedScreen(
    onNavigate: (String) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("For You", "Following", "Trending")
    val tracks = sampleAudioTracks()

    Column(modifier = Modifier.fillMaxSize()) {
        TopHeader(
            onSearchClick = { onNavigate("search_route") },
            onNotificationsClick = { onNavigate("notifications_route") }
        )
        FeedTabs(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )
        ReelsHomeScreen(
            tracks = tracks,
            modifier = Modifier.weight(1f),
            onCommentClick = { track ->
                onNavigate("comments_route/${track.id}")
            },
            onProfileClick = { track ->
                onNavigate("story_route/${track.id}")
            }
        )
    }
}
