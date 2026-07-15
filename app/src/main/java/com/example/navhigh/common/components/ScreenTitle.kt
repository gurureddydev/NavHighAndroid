package com.example.navhigh.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.NavHighTheme


data class TitlePart(
    val text: String,
    val color: Color = Color.White
)


@Composable
fun ScreenTitle(
    lines: List<List<TitlePart>>
) {

    lines.forEach { line ->

        Text(
            text = buildAnnotatedString {

                line.forEach { part ->

                    withStyle(
                        style = SpanStyle(
                            color = part.color
                        )
                    ) {

                        append(part.text)

                    }

                }

            },

            fontSize = AppTypography.EmailTitleSize,

            fontWeight = FontWeight.Bold
        )

    }

}


@Preview(
    showBackground = true
)
@Composable
fun ScreenTitlePreview() {

    NavHighTheme {

        ScreenTitle(

            lines = listOf(

                listOf(

                    TitlePart(
                        text = "What's your name? "
                    ),

                    TitlePart(
                        text = "password",
                        color = ForgotPasswordBlue
                    )

                )

            )

        )

    }

}