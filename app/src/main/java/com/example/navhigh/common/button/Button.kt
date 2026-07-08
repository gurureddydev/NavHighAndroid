package com.example.navhigh.common.button


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.DarkBackground
import com.example.navhigh.ui.theme.LoginButtonEnd
import com.example.navhigh.ui.theme.LoginButtonMiddle
import com.example.navhigh.ui.theme.LoginButtonStart


@Composable
fun Button(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .width(380.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(AppDimensions.TextFieldRadius)
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        LoginButtonStart,
                        LoginButtonMiddle,
                        LoginButtonEnd
                    )
                )
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFF020613
)
@Composable
private fun ButtonPreview() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {

        Button(
            text = "Log In",
            onClick = {}
        )
    }
}
