package com.example.navhigh.ui.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.common.textfield.FullNameTextField

import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme


@Composable
fun FullNameScreen(

    onBackClick: () -> Unit,

    onNextClick: (String) -> Unit,

    onLoginClick: () -> Unit,

    onContinueClick: () -> Unit

) {


    var fullName by remember {

        mutableStateOf("")

    }



    val configuration = LocalConfiguration.current

    val isTablet = configuration.screenWidthDp >= 600



    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(LoginBackground)

    ) {



        Column(

            modifier = Modifier

                .align(

                    if (isTablet)

                        Alignment.TopStart

                    else

                        Alignment.TopCenter

                )

                .statusBarsPadding()

                .padding(

                    start = if (isTablet)

                        120.dp

                    else

                        AppDimensions.EmailScreenHorizontalPadding,


                    end = AppDimensions.EmailScreenHorizontalPadding,


                    top = if (isTablet)

                        20.dp

                    else

                        AppDimensions.EmailScreenVerticalPadding

                )


                .widthIn(

                    max = if (isTablet)

                        440.dp

                    else

                        AppDimensions.EmailContentMaxWidth

                ),


            horizontalAlignment = Alignment.Start

        ) {



            BackArrow(

                onClick = onBackClick

            )




            Spacer(

                modifier = Modifier.height(

                    AppDimensions.EmailBackArrowSpacing

                )

            )





            ScreenTitle(

                lines = listOf(

                    listOf(

                        TitlePart(

                            text = "What's your name?"

                        )

                    )

                )

            )





            Spacer(

                modifier = Modifier.height(

                    if (isTablet)

                        20.dp

                    else

                        AppDimensions.EmailDescriptionSpacing

                )

            )





            Text(

                text = "Enter your full name so people can\nfind and recognize you.",


                color = Color.White.copy(

                    alpha = 0.70f

                )

            )






            Spacer(

                modifier = Modifier.height(

                    if (isTablet)

                        28.dp

                    else

                        20.dp

                )

            )





            FullNameTextField(

                value = fullName,


                onValueChange = {

                    fullName = it

                }

            )





            Spacer(

                modifier = Modifier.height(

                    if (isTablet)

                        28.dp

                    else

                        AppDimensions.EmailButtonSpacing

                )

            )







            Button(

                text = "Next",


                onClick = {


                    if(fullName.trim().isNotEmpty()){


                        onNextClick(

                            fullName.trim()

                        )


                    }


                }

            )






            Spacer(

                modifier = Modifier.weight(1f)

            )



        }





        Column(

            modifier = Modifier

                .align(Alignment.BottomCenter)

                .navigationBarsPadding()

                .fillMaxWidth()

                .padding(

                    bottom = 24.dp

                ),


            horizontalAlignment = Alignment.CenterHorizontally

        ) {



            AlreadyHaveAccount(

                onLogin = onLoginClick,

                onContinue = onContinueClick

            )


        }



    }


}









@Preview(

    showBackground = true,

    showSystemUi = true,

    name = "Phone Preview"

)

@Composable
fun FullNameScreenPhonePreview(){



    NavHighTheme {



        FullNameScreen(

            onBackClick = {},

            onNextClick = {},

            onLoginClick = {},

            onContinueClick = {}

        )


    }


}









@Preview(

    showBackground = true,

    showSystemUi = true,

    name = "Tablet Preview",

    device = "spec:width=800dp,height=1280dp,dpi=240"

)

@Composable
fun FullNameScreenTabletPreview(){



    NavHighTheme {



        FullNameScreen(

            onBackClick = {},

            onNextClick = {},

            onLoginClick = {},

            onContinueClick = {}

        )


    }


}