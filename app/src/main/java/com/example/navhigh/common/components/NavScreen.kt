package com.example.navhigh.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.navhigh.R
import com.example.navhigh.ui.theme.GradientEnd
import com.example.navhigh.ui.theme.GradientStart



// ---------------- LOGO ----------------

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    size: Dp = 102.dp
) {

    Image(

        painter = painterResource(
            id = R.drawable.nav_high_logo
        ),

        contentDescription = "NavHigh Logo",

        modifier = modifier.size(size)

    )

}



// ---------------- TITLE ----------------

@Composable
fun NavTitle(
    modifier: Modifier = Modifier,
    textSize: Dp = 36.dp
) {


    Row(

        modifier = modifier,

        verticalAlignment = Alignment.CenterVertically

    ) {


        Text(

            text = "Nav",

            style = TextStyle(

                color = Color.White,

                fontSize = textSize.value.sp,

                fontWeight = FontWeight.Bold

            )

        )



        Text(

            text = "High",

            style = TextStyle(

                brush = Brush.horizontalGradient(

                    colors = listOf(

                        GradientStart,

                        GradientEnd

                    )

                ),

                fontSize = textSize.value.sp,

                fontWeight = FontWeight.Bold

            )

        )


    }

}



// ---------------- TAGLINE ----------------

@Composable
fun NavTagline(
    modifier: Modifier = Modifier,
    textSize: Dp = 10.dp
) {


    Text(

        modifier = modifier,

        text = "Share Your Voice. Reach New Heights.",

        color = Color.White.copy(alpha = 0.9f),

        fontSize = textSize.value.sp,

        fontWeight = FontWeight.Normal,

        textAlign = TextAlign.Center

    )


}



// ---------------- BRAND ----------------

@Composable
fun NavBrand(

    modifier: Modifier = Modifier,

    topPadding: Int = 0,

    logoSize: Dp = 102.dp,

    titleSize: Dp = 36.dp,

    taglineSize: Dp = 10.dp,

    logoToTitleSpacing: Dp = 0.dp

) {


    Column(

        modifier = modifier
            .padding(
                top = topPadding.dp
            ),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        Spacer(

            modifier = Modifier.height(30.dp)

        )



        Logo(

            size = logoSize

        )



        Spacer(

            modifier = Modifier.height(logoToTitleSpacing)

        )



        NavTitle(

            textSize = titleSize

        )



        NavTagline(

            textSize = taglineSize

        )


    }


}



// ---------------- PREVIEW ----------------

@Preview(
    showBackground = true,
    backgroundColor = 0xFF020613
)
@Composable
fun NavBrandPreview() {


    Box(

        modifier = Modifier.fillMaxSize(),

        contentAlignment = Alignment.TopCenter

    ) {


        NavBrand()


    }

}