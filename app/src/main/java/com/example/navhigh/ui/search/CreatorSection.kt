package com.example.navhigh.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R

@Composable
fun CreatorSection() {
    var isExpanded by remember { mutableStateOf(false) }

    val creators = listOf(
        Triple("Arjun Beats", "@arjunbeats", "128K followers"),
        Triple("Ivana Voice", "@ivanavoice", "96K followers"),
        Triple("MusicLab", "@musiclab", "74K followers"),
        Triple("EchoFlow", "@echoflow", "100K followers")
    )

    Column(modifier = Modifier.padding(10.dp)) {
        CustomSectionHeader(
            title = "Creators",
            isExpanded = isExpanded,
            onSeeAllClick = { isExpanded = !isExpanded }
        )
        Spacer(modifier = Modifier.height(10.dp))

        val displayedCreators = if (isExpanded) creators else creators.take(3)

        displayedCreators.forEach { (name, handle, followers) ->
            val img = when(name) {
                "Arjun Beats" -> R.drawable.pro1_img
                "Ivana Voice" -> R.drawable.ivana
                "MusicLab" -> R.drawable.music
                else -> R.drawable.logo
            }
            CreatorItem(name, handle, followers, img)
        }
    }
}

@Composable
fun CreatorItem(
    name: String,
    handle: String,
    followers: String,
    imageRes: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF3DA9FC),
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = "$handle • $followers",
                color = Color.Gray,
                fontSize = 10.sp,
                lineHeight = 10.sp,
                maxLines = 1
            )
        }
    }
}
@Composable
fun CustomSectionHeader(title: String, isExpanded: Boolean, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        Text(
            text = if (isExpanded) "Show Less" else "See All",
            fontSize = 14.sp,
            color = Color(0xFF3DA9FC),
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun CreatorSectionPreview() {
    MaterialTheme {
        Surface(color = Color(0xFF121212)) {
            CreatorSection()
        }
    }
}