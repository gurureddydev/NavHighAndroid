package com.example.navhigh.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.R
import com.example.navhigh.ui.theme.AppDimensions


@Composable
fun BackArrow(
    onClick: () -> Unit
) {

    Image(
        painter = painterResource(
            id = R.drawable.left_arrow
        ),
        contentDescription = "Back Arrow",
        colorFilter = ColorFilter.tint(Color.White),
        modifier = Modifier
            .size(AppDimensions.BackButtonIconSize)
            .clickable {
                onClick()
            }
    )
}


@Preview(showBackground = true)
@Composable
fun BackArrowPreview() {

    BackArrow(
        onClick = {}
    )
}