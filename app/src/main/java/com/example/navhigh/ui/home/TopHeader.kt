
package com.example.navhigh.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.PrimaryBlue

@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo and Brand Name Layout
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.nav),
                contentDescription = "NavHigh Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = "Nav",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "High",
                color = PrimaryBlue, // Light Blue accent matching image_a41c1f.jpg
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Search Action Control
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }

        // Notification Badged Action Control (No offset used)
        IconButton(onClick = { }) {
            BadgedBox(
                badge = {
                    Badge(containerColor = PrimaryBlue) {
                        Text("3", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }
        }
    }
}

// ==========================================
// PREVIEW LAYOUT 
// ==========================================
@Preview(name = "Header Preview", showBackground = true, backgroundColor = 0xFF030712)
@Composable
fun TopHeaderPreview() {
    NavHighTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF030712)) // Matches the dark slate context of your UI
        ) {
            TopHeader()
        }
    }
}

