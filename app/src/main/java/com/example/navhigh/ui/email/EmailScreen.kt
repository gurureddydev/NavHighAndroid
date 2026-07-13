package com.example.navhigh.ui.email


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.textfield.EmailTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ErrorNeonRose
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme

import kotlinx.coroutines.delay



@Composable
fun EmailScreen(

    onNextClick: (String) -> Unit = {},

    onBackClick: () -> Unit = {},

    onLoginClick: () -> Unit = {}

) {


    var email by remember {

        mutableStateOf("")

    }


    var emailError by remember {

        mutableStateOf(false)

    }


    var isLoading by remember {

        mutableStateOf(false)

    }




    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(LoginBackground)

    ) {



        Column(

            modifier = Modifier

                .widthIn(
                    max = AppDimensions.EmailContentMaxWidth
                )

                .fillMaxSize()

                .padding(

                    horizontal = AppDimensions.EmailScreenHorizontalPadding,

                    vertical = AppDimensions.EmailScreenVerticalPadding

                ),


            verticalArrangement = Arrangement.SpaceBetween,


            horizontalAlignment = Alignment.Start


        ) {



            Column {


                Image(

                    painter = painterResource(

                        id = R.drawable.left_arrow

                    ),

                    contentDescription = "Back Arrow",

                    colorFilter = ColorFilter.tint(Color.White),

                    modifier = Modifier

                        .size(

                            AppDimensions.EmailBackArrowSize

                        )

                        .clickable {

                            onBackClick()

                        }

                )



                Spacer(

                    modifier = Modifier.height(

                        AppDimensions.EmailBackArrowSpacing

                    )

                )



                Text(

                    text = "What's your",

                    color = Color.White,

                    fontSize = AppTypography.EmailTitleSize,

                    fontWeight = FontWeight.Bold

                )



                Text(

                    text = "email?",

                    color = ForgotPasswordBlue,

                    fontSize = AppTypography.EmailTitleSize,

                    fontWeight = FontWeight.Bold

                )



                Spacer(

                    modifier = Modifier.height(

                        AppDimensions.EmailDescriptionSpacing

                    )

                )



                Text(

                    text = "Enter the email where you can\nbe contacted. No one will see this\non your profile.",

                    color = Color.LightGray,

                    fontSize = AppTypography.EmailDescriptionSize,

                    lineHeight = AppTypography.EmailDescriptionLineHeight

                )
                Spacer(

                    modifier = Modifier.height(

                        AppDimensions.EmailTextFieldSpacing

                    )

                )





                EmailTextField(

                    value = email,

                    isError = emailError,

                    onValueChange = {


                        email = it


                        emailError = false


                    }

                )





                if(emailError){



                    Spacer(

                        modifier = Modifier.height(

                            AppDimensions.EmailErrorSpacing

                        )

                    )





                    Text(

                        text = "Enter a correct email address,\nlike name@example.com",


                        color = ErrorNeonRose,


                        fontSize = AppTypography.EmailDescriptionSize,


                        lineHeight = AppTypography.EmailDescriptionLineHeight

                    )


                }





                Spacer(

                    modifier = Modifier.height(

                        AppDimensions.EmailButtonSpacing

                    )

                )






                Button(

                    text = "Next",


                    isLoading = isLoading,


                    onClick = {



                        val emailPattern = Regex(

                            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"

                        )




                        if(email.matches(emailPattern)){



                            emailError = false



                            isLoading = true




                        }else{



                            emailError = true




                        }



                    }


                )


            }




            // THIS WILL STAY AT THE BOTTOM

            AlreadyHaveAccount(

                onLogin = {


                    onLoginClick()


                },


                onContinue = {


                }


            )



        }


    }





    if(isLoading){



        LaunchedEffect(Unit){



            delay(5000)



            isLoading = false



            onNextClick(email)



        }


    }


}
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun EmailScreenPhonePreview() {

    NavHighTheme {

        EmailScreen()

    }

}





@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun EmailScreenTabletPreview() {

    NavHighTheme {

        EmailScreen()

    }

}