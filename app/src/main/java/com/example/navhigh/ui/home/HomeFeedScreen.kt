package com.example.navhigh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.Background
import com.example.navhigh.ui.theme.NavHighTheme

@Composable
fun HomeFeedScreen() {

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            BottomNavigationBar()
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Background,
                            Color(0xFF000510)
                        )
                    )
                )
                .padding(padding)
        ) {

            LazyColumn {

                item {
                    TopHeader()
                }

                item {
                    FeedTabs()
                }

                item {
                    StorySection()
                }

                item {
                    // First clip plays automatically
                    AudioPostCard(
                        title = "Featured Clip 🎵",
                        audioResId = R.raw.audio_1,
                        autoPlay = true
                    )
                }

                item {
                    AudioPostCard()
                }

                item {
                    AudioPostCard()
                }

                item {
                    Spacer(
                        modifier = Modifier.height(140.dp)
                    )
                }
            }

//            MiniPlayer(
//                modifier = Modifier
//                    .align(androidx.compose.ui.Alignment.BottomCenter)
//            )
        }
    }
}

// I've added a preview to HomeFeedScreen.kt for you to check the layout:
@Preview(showBackground = true)
@Composable
fun HomeFeedPreview() {
    NavHighTheme {
        HomeFeedScreen()
    }
}
