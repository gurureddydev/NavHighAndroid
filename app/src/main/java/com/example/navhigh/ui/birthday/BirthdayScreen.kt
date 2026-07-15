package com.example.navhigh.ui.birthday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground


@Composable
fun BirthdayScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onLoginClick: () -> Unit,
    onContinueClick: () -> Unit
) {


    var birthday by remember {
        mutableStateOf("")
    }


    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(
                LoginBackground
            )

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

                )

                .align(
                    Alignment.Center
                ),


            verticalArrangement = Arrangement.SpaceBetween,


            horizontalAlignment = Alignment.Start

        ) {


            Column {


                BackArrow(

                    onClick = {

                        onBackClick()

                    }

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

                                text = "What's your "

                            ),

                            TitlePart(

                                text = "birthday?",

                                color = ForgotPasswordBlue

                            )

                        )

                    )

                )



                Spacer(

                    modifier = Modifier.height(15.dp)

                )



                Text(

                    text = buildAnnotatedString {


                        withStyle(

                            style = SpanStyle(

                                color = Color.White

                            )

                        ) {


                            append(

                                "Use your own birthday, even if this\n" +
                                        "account is for a business, a pet or\n" +
                                        "something else. No one will see this\n" +
                                        "unless you choose to share it. "

                            )


                        }



                        withStyle(

                            style = SpanStyle(

                                color = Color(0xFF00BFFF)

                            )

                        ) {


                            append(

                                "Why do I\n" +
                                        "need to provide my birthday?"

                            )


                        }


                    },


                    fontSize = 12.sp,


                    lineHeight = 20.sp

                )



                Spacer(

                    modifier = Modifier.height(10.dp)

                )



                OutlinedTextField(

                    value = birthday,


                    onValueChange = {

                        birthday = it

                    },


                    label = {

                        Text(

                            text = "Birthday (0 years old)",

                            color = Color.LightGray

                        )

                    },


                    placeholder = {

                        Text(

                            text = "July 3, 2026",

                            color = Color.White

                        )

                    },


                    singleLine = true,


                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = Color(0xFF1677FF),

                        unfocusedBorderColor = Color(0xFF1677FF),

                        focusedTextColor = Color.White,

                        unfocusedTextColor = Color.White,

                        cursorColor = Color.White

                    ),


                    shape = RoundedCornerShape(30.dp),


                    modifier = Modifier

                        .fillMaxSize()

                        .height(

                            AppDimensions.TextFieldHeight

                        )

                )



                Spacer(

                    modifier = Modifier.height(45.dp)

                )



                Button(

                    text = "Next",

                    onClick = {


                        if(birthday.isNotBlank()) {


                            onNextClick()


                        }


                    }

                )


            }



            AlreadyHaveAccount(

                onLogin = {

                    onLoginClick()

                },


                onContinue = {

                    onContinueClick()

                }

            )


        }


    }


}



@Preview(

    showBackground = true,

    showSystemUi = true

)
@Composable
fun BirthdayScreenPreview(){


    BirthdayScreen(

        onBackClick = {},

        onNextClick = {},

        onLoginClick = {},

        onContinueClick = {}

    )

}