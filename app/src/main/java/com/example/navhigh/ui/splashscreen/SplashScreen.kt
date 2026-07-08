package com.example.navhigh.ui.splashscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

import com.example.navhigh.common.components.NavBrand
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.DarkBackground
import com.example.navhigh.ui.theme.NavHighTheme



@Composable
fun SplashScreen() {


    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)

    ) {



        // LOGO + TITLE + TAGLINE

        Column(

            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(
                    top = AppDimensions.SplashTopPadding
                ),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            NavBrand(

                logoSize = AppDimensions.SplashLogoSize,

                titleSize = AppDimensions.SplashTitleSize,

                taglineSize = AppDimensions.SplashTaglineSize,

                logoToTitleSpacing = AppDimensions.SplashLogoTitleSpacing

            )


        }





        // LOADING SECTION

        Column(

            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = AppDimensions.SplashLoadingBottomPadding
                ),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {



            // LOADING BAR

            Box(

                modifier = Modifier
                    .width(
                        AppDimensions.SplashLoadingBarWidth
                    )
                    .height(
                        AppDimensions.SplashLoadingBarHeight
                    )
                    .clip(
                        RoundedCornerShape(
                            AppDimensions.SplashLoadingRadius
                        )
                    )
                    .background(
                        Color(0xFF2B3147)
                    )

            ) {



                // PROGRESS

                Box(

                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.42f)
                        .clip(
                            RoundedCornerShape(
                                AppDimensions.SplashLoadingRadius
                            )
                        )
                        .background(
                            Color(0xFF2C8DFF)
                        )

                )


            }





            Spacer(

                modifier = Modifier.height(
                    AppDimensions.SplashLoadingTextSpacing
                )

            )





            Text(

                text = "Loading...",

                color = Color.White.copy(
                    alpha = 0.75f
                ),

                fontSize = AppTypography.SplashLoadingTextSize,

                fontWeight = FontWeight.Normal,

                letterSpacing = 1.sp

            )


        }


    }


}





@Preview(

    showBackground = true,

    showSystemUi = true

)

@Composable
fun SplashScreenPreview() {


    NavHighTheme {


        SplashScreen()


    }

}