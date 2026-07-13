package com.example.navhigh.ui.password


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview


import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.dialoguebox.AlreadyHaveAccountDialog
import com.example.navhigh.common.textfield.PasswordTextField

import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.Background
import com.example.navhigh.ui.theme.BlueEnd
import com.example.navhigh.ui.theme.BlueStart
import com.example.navhigh.ui.theme.BorderBlue
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.PasswordDescriptionColor
import com.example.navhigh.ui.theme.White



@Composable
fun PasswordScreen(

    onBackClick: () -> Unit = {},

    onNextClick: () -> Unit = {},

    onLoginClick: () -> Unit = {}

) {


    var password by remember {

        mutableStateOf("")

    }


    // Checkbox starts unchecked

    var rememberLogin by remember {

        mutableStateOf(false)

    }


    var showLoginDialog by remember {

        mutableStateOf(false)

    }



    val gradient = Brush.horizontalGradient(

        colors = listOf(

            BlueStart,

            BlueEnd

        )

    )



    BoxWithConstraints(

        modifier = Modifier

            .fillMaxSize()

            .background(Background)

    ) {



        val contentWidth = if(maxWidth > AppDimensions.TabletBreakpoint){

            AppDimensions.PasswordTabletContentMaxWidth

        }else{

            AppDimensions.PasswordContentMaxWidth

        }



        Column(

            modifier = Modifier

                .widthIn(

                    max = contentWidth

                )

                .fillMaxSize()

                .align(Alignment.TopCenter)

                .padding(

                    horizontal = AppDimensions.ScreenPadding

                )

        ) {


            Spacer(

                modifier = Modifier.height(

                    AppDimensions.TopSpace

                )

            )



            Image(

                painter = painterResource(

                    id = R.drawable.left_arrow

                ),

                contentDescription = "Back",

                modifier = Modifier

                    .size(

                        AppDimensions.BackButtonIconSize

                    )

                    .clickable {

                        onBackClick()

                    },

                colorFilter = ColorFilter.tint(

                    White

                )

            )



            Spacer(

                modifier = Modifier.height(

                    AppDimensions.BackToTitle

                )

            )

            Text(

                text = buildAnnotatedString {


                    append("Create a ")



                    withStyle(

                        SpanStyle(

                            brush = gradient

                        )

                    ){

                        append("password")

                    }


                },


                color = White,

                fontSize = AppTypography.Title,

                fontWeight = FontWeight.Bold

            )





            Spacer(

                modifier = Modifier.height(

                    AppDimensions.TitleToDescription

                )

            )





            Text(

                text =

                    "Create a password with at least 6 letters\nor numbers. It should be something\nothers can't guess.",



                color = PasswordDescriptionColor,

                fontSize = AppTypography.Description,

                lineHeight = AppTypography.PasswordDescriptionLineHeight

            )





            Spacer(

                modifier = Modifier.height(

                    AppDimensions.DescriptionToTextField

                )

            )





            PasswordTextField(

                value = password,

                onValueChange = {

                    password = it

                },

                modifier = Modifier.fillMaxWidth()

            )





            Spacer(

                modifier = Modifier.height(

                    AppDimensions.TextFieldToRemember

                )

            )





            Row(

                verticalAlignment = Alignment.CenterVertically

            ){



                CustomCheckbox(

                    checked = rememberLogin,

                    onCheckedChange = {

                        rememberLogin = it

                    }

                )





                Spacer(

                    modifier = Modifier.width(

                        AppDimensions.RememberGap

                    )

                )





                Text(

                    text = "Remember login info.",

                    color = White,

                    fontSize = AppTypography.RememberText

                )





                Spacer(

                    modifier = Modifier.width(

                        AppDimensions.RememberTextSpacing

                    )

                )





                Text(

                    text = "Learn more",

                    color = BlueEnd,

                    fontSize = AppTypography.RememberText

                )


            }






            Spacer(

                modifier = Modifier.height(

                    AppDimensions.RememberToButton

                )

            )







            Button(

                text = "Next",

                modifier = Modifier.fillMaxWidth(),

                onClick = {

                    onNextClick()

                }

            )







            Spacer(

                modifier = Modifier.weight(1f)

            )






            AlreadyHaveAccount(

                onLogin = {

                    showLoginDialog = true

                },

                onContinue = {

                }

            )






            Spacer(

                modifier = Modifier.height(

                    AppDimensions.BottomSpace

                )

            )



        }


    }





    if(showLoginDialog){


        AlreadyHaveAccountDialog(


            onDismiss = {

                showLoginDialog = false

            },


            onContinue = {

                showLoginDialog = false

            },


            onLogin = {

                showLoginDialog = false

                onLoginClick()

            }

        )


    }


}

@Composable
fun CustomCheckbox(

    checked: Boolean,

    onCheckedChange: (Boolean) -> Unit

) {


    Box(

        modifier = Modifier

            .size(

                AppDimensions.CheckboxSize

            )

            .clip(

                RoundedCornerShape(

                    AppDimensions.CheckboxCornerRadius

                )

            )

            .border(

                width = AppDimensions.CheckboxBorderWidth,

                color = BorderBlue,

                shape = RoundedCornerShape(

                    AppDimensions.CheckboxCornerRadius

                )

            )

            .clickable {

                onCheckedChange(!checked)

            },


        contentAlignment = Alignment.Center

    ){



        if(checked){


            Icon(

                imageVector = Icons.Default.Check,

                contentDescription = null,

                tint = BorderBlue,

                modifier = Modifier.size(

                    AppDimensions.CheckboxTickSize

                )

            )


        }


    }


}








@Preview(

    showSystemUi = true,

    showBackground = true,

    device = "spec:width=412dp,height=915dp,dpi=420"

)

@Composable
fun PasswordPhonePreview(){


    NavHighTheme {


        PasswordScreen()


    }


}








@Preview(

    showSystemUi = true,

    showBackground = true,

    device = "spec:width=800dp,height=1280dp,dpi=240"

)

@Composable
fun PasswordTabletPreview(){


    NavHighTheme {


        PasswordScreen()


    }


}