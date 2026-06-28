package com.example.navhigh.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddCaptionSection() {
    var caption by remember { mutableStateOf("") }
    var showEmojiMenu by remember { mutableStateOf(false) }
    val emojis = listOf("😀", "😂", "🥰", "😎", "🤔", "🔥", "✨", "❤️")

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("3. Add Caption (Optional)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("${caption.length}/300", color = Color.Gray, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Input Field Box
        Box {
            OutlinedTextField(
                value = caption,
                onValueChange = { if (it.length <= 300) caption = it },
                // Messaging style configuration
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("What's on your mind?", color = Color.Gray.copy(alpha = 0.5f)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF161A22),
                    unfocusedContainerColor = Color(0xFF161A22),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.SentimentSatisfied,
                        contentDescription = "Emoji",
                        tint = Color.Gray,
                        modifier = Modifier
                            .clickable { showEmojiMenu = true }.padding(top =30.dp )
                            .size(20.dp)

                    )
                }
            )

            // Emoji Menu
            DropdownMenu(
                expanded = showEmojiMenu,
                onDismissRequest = { showEmojiMenu = false },
                modifier = Modifier.background(Color(0xFF1E2430))
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    emojis.forEach { emoji ->
                        Text(
                            text = emoji,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding( 4.dp)
                                .clickable {
                                    caption += emoji
                                    showEmojiMenu = false
                                }
                        )
                    }
                }
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun PreviewCaptionSection() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF0B0D13)) {
        AddCaptionSection()
    }
}