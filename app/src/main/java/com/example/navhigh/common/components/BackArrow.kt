package com.example.navhigh.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.R
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.ForgotPasswordBlue

@Composable
fun BackArrow(
    onClick: () -> Unit
) {

    Image(
        painter = painterResource(
            id = R.drawable.arrow
        ),
        contentDescription = "Back Arrow",
        colorFilter = ColorFilter.tint(ForgotPasswordBlue),
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