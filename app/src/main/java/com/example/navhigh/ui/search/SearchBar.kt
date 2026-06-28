package com.example.navhigh.ui.search

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier // Added modifier to control position externally
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val cyclingWords = remember { listOf("audio", "playlists", "Hashtags", "creators") }
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentIndex = (currentIndex + 1) % cyclingWords.size
        }
    }

    Box(
        modifier = modifier // Apply the modifier here
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(SearchBarBackground),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = IconAndTextColor,
                modifier = Modifier.size(20.dp)
            )

            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                textStyle = TextStyle(color = IconAndTextColor, fontSize = 14.sp),
                cursorBrush = SolidColor(Color.White.copy(alpha = 0.8f)),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch(searchQuery)
                    searchQuery = ""
                    focusManager.clearFocus()
                }),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 9.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchQuery.isEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Search ", color = IconAndTextColor.copy(alpha = 0.6f), fontSize = 12.sp)

                                AnimatedContent(
                                    targetState = cyclingWords[currentIndex],
                                    transitionSpec = {
                                        (fadeIn() + slideInVertically { it / 2 }) togetherWith
                                                (fadeOut() + slideOutVertically { -it / 2 })
                                    },
                                    label = "CyclingText"
                                ) { targetWord ->
                                    Text(
                                        text = targetWord,
                                        color = IconAndTextColor.copy(alpha = 0.6f),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        innerTextField()
                    }
                }
            )

            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Voice Search",
                tint = IconAndTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0F)
@Composable
fun SearchBarPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SearchBar(onSearch = {})
        }
    }
}