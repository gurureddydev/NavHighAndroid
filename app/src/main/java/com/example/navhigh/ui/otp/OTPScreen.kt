package com.example.navhigh.ui.otp


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview


import com.example.navhigh.R

import com.example.navhigh.common.button.Button

import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(

    email: String,

    onBackClick: () -> Unit = {},

    onNextClick: () -> Unit = {}

) {


    var otp by remember {

        mutableStateOf("")

    }


    var isError by remember {

        mutableStateOf(false)

    }


    var showResendSheet by remember {

        mutableStateOf(false)

    }


    val focusRequester = remember {

        FocusRequester()

    }



    BoxWithConstraints(

        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)

    ) {


        val contentWidth = if (maxWidth > AppDimensions.TabletBreakpoint) {

            AppDimensions.OtpTabletContentMaxWidth

        } else {

            AppDimensions.OtpContentMaxWidth

        }



        Column(

            modifier = Modifier

                .widthIn(

                    max = contentWidth

                )

                .fillMaxSize()

                .align(Alignment.TopCenter)

                .imePadding()

                .padding(

                    horizontal = AppDimensions.OtpScreenHorizontalPadding,

                    vertical = AppDimensions.OtpScreenVerticalPadding

                ),


            horizontalAlignment = Alignment.Start

        ) {



            // BACK ARROW


            Image(

                painter = painterResource(

                    id = R.drawable.left_arrow

                ),

                contentDescription = "Back",

                colorFilter = ColorFilter.tint(Color.White),


                modifier = Modifier

                    .size(

                        AppDimensions.OtpBackArrowSize

                    )

                    .clickable {

                        onBackClick()

                    }

            )




            Spacer(

                modifier = Modifier.height(

                    AppDimensions.OtpBackArrowSpacing

                )

            )





            Text(

                text = "Enter the",

                color = Color.White,

                fontSize = AppTypography.EmailTitleSize,

                fontWeight = FontWeight.Bold

            )





            Row {


                Text(

                    text = "confirmation ",

                    color = Color.White,

                    fontSize = AppTypography.EmailTitleSize,

                    fontWeight = FontWeight.Bold

                )



                Text(

                    text = "code",

                    color = ForgotPasswordBlue,

                    fontSize = AppTypography.EmailTitleSize,

                    fontWeight = FontWeight.Bold

                )


            }




            Spacer(

                modifier = Modifier.height(

                    AppDimensions.OtpTitleSpacing

                )

            )





            Text(

                text = "To confirm your profile, enter the 6-digit code we sent to",

                color = Color.LightGray,

                fontSize = AppTypography.EmailDescriptionSize

            )




            Text(

                text = email,

                color = Color(0xFF00C8FF),

                fontSize = AppTypography.EmailDescriptionSize

            )





            Spacer(

                modifier = Modifier.height(

                    AppDimensions.OtpEmailTextSpacing

                )

            )





            Box(

                modifier = Modifier

                    .fillMaxWidth()

                    .clickable {

                        focusRequester.requestFocus()

                    }

            ) {



                BasicTextField(

                    value = otp,


                    onValueChange = {


                        if(it.length <= 6){

                            otp = it

                            isError = false

                        }

                    },


                    keyboardOptions = KeyboardOptions(

                        keyboardType = KeyboardType.Number,

                        imeAction = ImeAction.Done

                    ),


                    modifier = Modifier

                        .focusRequester(focusRequester)

                        .fillMaxWidth(),



                    decorationBox = {



                        Row(

                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.SpaceBetween

                        ){



                            repeat(6){ index ->



                                val value =

                                    if(index < otp.length)

                                        otp[index].toString()

                                    else

                                        ""



                                Box(

                                    modifier = Modifier

                                        .size(

                                            AppDimensions.OtpBoxSize

                                        )

                                        .border(

                                            width = AppDimensions.OtpBorderWidth,


                                            color = if(isError)

                                                Color.Red

                                            else

                                                ForgotPasswordBlue,


                                            shape = RoundedCornerShape(

                                                AppDimensions.OtpBoxRadius

                                            )

                                        ),


                                    contentAlignment = Alignment.Center

                                ){



                                    Text(

                                        text = value,

                                        color = Color.White,

                                        fontSize = AppTypography.EmailDescriptionSize,

                                        fontWeight = FontWeight.Bold

                                    )


                                }



                            }



                        }



                    }


                )


            }
            if (isError) {


                Text(

                    text = "Invalid confirmation code",

                    color = Color.Red,

                    fontSize = AppTypography.EmailDescriptionSize,

                    modifier = Modifier.padding(

                        top = AppDimensions.OtpErrorTextPadding

                    )

                )


            }





            Spacer(

                modifier = Modifier.height(

                    AppDimensions.OtpButtonSpacing

                )

            )






            Button(

                text = "Next",


                onClick = {


                    if (otp.length == 6) {


                        onNextClick()


                    }

                    else {


                        isError = true


                    }


                }


            )







            Spacer(

                modifier = Modifier.height(

                    AppDimensions.OtpResendButtonSpacing

                )

            )







            OutlinedButton(


                onClick = {


                    showResendSheet = true


                },


                modifier = Modifier

                    .fillMaxWidth()

                    .height(

                        AppDimensions.OtpResendButtonHeight

                    ),



                shape = RoundedCornerShape(

                    AppDimensions.OtpResendButtonRadius

                ),



                border = BorderStroke(

                    AppDimensions.OtpBorderWidth,

                    ForgotPasswordBlue

                )


            ) {



                Text(

                    text = "I didn’t get the code",

                    color = Color.White,

                    fontSize = AppTypography.EmailDescriptionSize

                )


            }




        }







        // BOTTOM SHEET


        if(showResendSheet){



            ModalBottomSheet(


                onDismissRequest = {


                    showResendSheet = false


                },


                containerColor = LoginBackground,


                shape = RoundedCornerShape(


                    topStart = AppDimensions.OtpSheetRadius,

                    topEnd = AppDimensions.OtpSheetRadius


                )



            ){



                Column(


                    modifier = Modifier

                        .widthIn(

                            max = AppDimensions.OtpSheetMaxWidth

                        )

                        .fillMaxWidth()

                        .align(Alignment.CenterHorizontally)

                        .padding(

                            AppDimensions.OtpSheetPadding

                        )


                ){





                    Text(


                        text = "✕",


                        color = Color.White,


                        fontSize = AppTypography.EmailTitleSize,


                        modifier = Modifier

                            .clickable {


                                showResendSheet = false


                            }


                    )







                    Spacer(


                        modifier = Modifier.height(


                            AppDimensions.OtpSheetTopSpacing


                        )


                    )







                    Column(



                        modifier = Modifier

                            .fillMaxWidth()

                            .border(



                                width = AppDimensions.OtpBorderWidth,



                                color = ForgotPasswordBlue,



                                shape = RoundedCornerShape(



                                    AppDimensions.OtpSheetBoxRadius



                                )



                            )



                    ){





                        Text(



                            text = "Resend confirmation code",



                            color = Color.White,



                            fontSize = AppTypography.EmailDescriptionSize,



                            modifier = Modifier



                                .fillMaxWidth()



                                .padding(



                                    AppDimensions.OtpSheetItemPadding



                                )



                                .clickable {



                                    showResendSheet = false



                                }



                        )








                        HorizontalDivider(



                            color = ForgotPasswordBlue



                        )









                        Text(



                            text = "Change email",



                            color = Color.White,



                            fontSize = AppTypography.EmailDescriptionSize,



                            modifier = Modifier



                                .fillMaxWidth()



                                .padding(



                                    AppDimensions.OtpSheetItemPadding



                                )



                                .clickable {



                                    showResendSheet = false



                                }



                        )



                    }







                    Spacer(



                        modifier = Modifier.height(



                            AppDimensions.OtpSheetBottomSpacing



                        )



                    )





                }





            }



        }




    }



}

@Composable
fun OtpBottomSheetPreviewContent() {


    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(LoginBackground)

    ) {



        Column(

            modifier = Modifier

                .fillMaxWidth()

                .align(Alignment.BottomCenter)

                .widthIn(

                    max = AppDimensions.OtpSheetMaxWidth

                )

                .background(

                    LoginBackground,

                    RoundedCornerShape(

                        topStart = AppDimensions.OtpSheetRadius,

                        topEnd = AppDimensions.OtpSheetRadius

                    )

                )

                .padding(

                    AppDimensions.OtpSheetPadding

                )


        ) {



            Text(

                text = "✕",

                color = Color.White,

                fontSize = AppTypography.EmailTitleSize

            )





            Spacer(

                modifier = Modifier.height(

                    AppDimensions.OtpSheetTopSpacing

                )

            )





            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .border(

                        width = AppDimensions.OtpBorderWidth,

                        color = ForgotPasswordBlue,

                        shape = RoundedCornerShape(

                            AppDimensions.OtpSheetBoxRadius

                        )

                    )

            ) {



                Text(

                    text = "Resend confirmation code",

                    color = Color.White,

                    fontSize = AppTypography.EmailDescriptionSize,

                    modifier = Modifier

                        .fillMaxWidth()

                        .padding(

                            AppDimensions.OtpSheetItemPadding

                        )

                )





                HorizontalDivider(

                    color = ForgotPasswordBlue

                )





                Text(

                    text = "Change email",

                    color = Color.White,

                    fontSize = AppTypography.EmailDescriptionSize,

                    modifier = Modifier

                        .fillMaxWidth()

                        .padding(

                            AppDimensions.OtpSheetItemPadding

                        )

                )



            }







            Spacer(

                modifier = Modifier.height(

                    AppDimensions.OtpSheetBottomSpacing

                )

            )


        }


    }


}







// PHONE PREVIEW

@Preview(

    showBackground = true,

    showSystemUi = true,

    device = "spec:width=412dp,height=915dp,dpi=420"

)

@Composable
fun OtpPhonePreview(){


    NavHighTheme {


        OtpScreen(

            email = "futuretech662006@gmail.com"

        )


    }


}








// TABLET PREVIEW

@Preview(

    showBackground = true,

    showSystemUi = true,

    device = "spec:width=800dp,height=1280dp,dpi=240"

)

@Composable
fun OtpTabletPreview(){


    NavHighTheme {


        OtpScreen(

            email = "futuretech662006@gmail.com"

        )


    }


}








// BOTTOM SHEET PREVIEW

@Preview(

    showBackground = true,

    showSystemUi = true,

    device = "spec:width=412dp,height=915dp,dpi=420"

)

@Composable
fun OtpBottomSheetPreview(){


    NavHighTheme {


        OtpBottomSheetPreviewContent()


    }


}