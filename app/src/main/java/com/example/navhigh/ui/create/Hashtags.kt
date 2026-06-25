package com.example.navhigh.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddHashtagsSection() {
    val hashtags = listOf("#podcast", "#audio", "#motivation", "#story")

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = "4. Add Hashtags (Optional)",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            hashtags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .background(Color(0xFF161A22), RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    Text(text = tag, color = Color(0xFF2196F3), fontSize = 9.sp)
                }
            }

            // Add Button - Matched style to the podcast box
            Box(
                modifier = Modifier
                    .background(Color(0xFF161A22), RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}

// --- PREVIEW SECTION ---
@Preview(showBackground = true)
@Composable
fun PreviewHashtagsSection() {
    // Set background color to match your app's dark theme
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF0B0D13)) {
        AddHashtagsSection()
    }
}