package com.example.navhigh.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.PrimaryBlue

@Composable
fun FollowButton() {
    OutlinedButton(
        onClick = {},
        modifier = Modifier.height(28.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            0.dp,
            PrimaryBlue
        ),
        contentPadding = PaddingValues(horizontal = 18.dp)
    ) {
        Text(
            text = "Follow",
            color = PrimaryBlue,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}