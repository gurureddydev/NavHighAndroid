package com.example.navhigh.ui.name


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

import com.example.navhigh.R
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.common.textfield.UsernameTextField
import com.example.navhigh.common.button.Button

import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme



@Composable
fun UserNameScreen(

    fullName: String,

    onBackClick: () -> Unit,

    onNextClick: () -> Unit,

    onLoginClick: () -> Unit,

    onContinueClick: () -> Unit

) {



    val generatedUsername = remember(fullName) {

        "@" + fullName

            .lowercase()

            .replace(" ", "")

    }



    var username by remember {

        mutableStateOf(generatedUsername)

    }



    var showTick by remember {

        mutableStateOf(true)

    }



    var isLoading by remember {

        mutableStateOf(false)

    }




    LaunchedEffect(isLoading) {


        if(isLoading){


            delay(5 * 60 * 1000L)


            isLoading = false


            showTick = true


        }


    }






    val configuration = LocalConfiguration.current


    val isTablet = configuration.screenWidthDp >= 600





    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(LoginBackground)

    ){





        Column(

            modifier = Modifier

                .align(Alignment.TopCenter)

                .statusBarsPadding()

                .fillMaxWidth()

                .widthIn(

                    max = AppDimensions.EmailContentMaxWidth

                )

                .padding(

                    horizontal = AppDimensions.EmailScreenHorizontalPadding,

                    vertical = if(isTablet)

                        60.dp

                    else

                        AppDimensions.EmailScreenVerticalPadding

                ),


            horizontalAlignment = Alignment.Start

        ){





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

                            text = "Create a username"

                        )

                    )

                )

            )







            Spacer(

                modifier = Modifier.height(16.dp)

            )







            Text(

                text = "Add a username or use our suggestion.\nYou can change this at any time.",


                color = Color.White.copy(

                    alpha = 0.70f

                )

            )








            Spacer(

                modifier = Modifier.height(28.dp)

            )









            Box(

                modifier = Modifier.fillMaxWidth()

            ){





                UsernameTextField(

                    value = username,


                    onValueChange = {


                        username = it


                        showTick = false


                    },


                    isAvailable = showTick

                )





                if(showTick){



                    Icon(

                        painter = painterResource(

                            id = R.drawable.tick_icon

                        ),


                        contentDescription = "Username available",


                        modifier = Modifier

                            .align(Alignment.CenterEnd)

                            .padding(end = 16.dp)

                            .size(28.dp)

                    )


                }



            }










            Spacer(

                modifier = Modifier.height(

                    AppDimensions.EmailButtonSpacing

                )

            )









            Button(

                text = if(isLoading)

                    ""

                else

                    "Next",



                onClick = {


                    if(username.trim().length > 1){


                        showTick = true


                        isLoading = true


                        onNextClick()


                    }


                }

            )



            if(isLoading){



                CircularProgressIndicator(

                    modifier = Modifier

                        .padding(top = 12.dp)

                        .size(24.dp),


                    color = Color.White

                )


            }







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

                    horizontal = AppDimensions.EmailScreenHorizontalPadding,

                    vertical = 24.dp

                ),


            horizontalAlignment = Alignment.CenterHorizontally

        ){



            AlreadyHaveAccount(

                onLogin = onLoginClick,

                onContinue = onContinueClick

            )



        }





    }





}









@Preview(

    showBackground = true,

    showSystemUi = true

)

@Composable
fun UserNamePreview(){


    NavHighTheme{


        UserNameScreen(

            fullName = "Poorna Prakash",

            onBackClick = {},

            onNextClick = {},

            onLoginClick = {},

            onContinueClick = {}

        )


    }


}