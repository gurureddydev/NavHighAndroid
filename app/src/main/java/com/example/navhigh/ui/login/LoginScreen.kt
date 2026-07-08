package com.example.navhigh.ui.login


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.NavBrand
import com.example.navhigh.common.textfield.PasswordTextField
import com.example.navhigh.common.textfield.UsernameTextField


import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.CreateAccountColor
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.GoogleButtonBorderColor
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.LoginDividerColor
import com.example.navhigh.ui.theme.LoginWhiteText
import com.example.navhigh.ui.theme.NavHighTheme



@Composable
fun LoginScreen() {


    var username by remember {

        mutableStateOf("")

    }


    var password by remember {

        mutableStateOf("")

    }



    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
            .padding(

                horizontal = AppDimensions.ScreenHorizontalPadding,

                vertical = AppDimensions.ScreenVerticalPadding

            ),


        horizontalAlignment = Alignment.CenterHorizontally,


        verticalArrangement = Arrangement.Top

    ) {



        // NAV BRAND

        NavBrand()



        Spacer(

            modifier = Modifier.height(

                AppDimensions.LogoToFieldSpacing

            )

        )



        // USERNAME FIELD

        UsernameTextField(

            value = username,

            onValueChange = {

                username = it

            }

        )



        Spacer(

            modifier = Modifier.height(

                AppDimensions.FieldSpacing

            )

        )



        // PASSWORD FIELD

        PasswordTextField(

            value = password,

            onValueChange = {

                password = it

            }

        )



        Spacer(

            modifier = Modifier.height(

                AppDimensions.LoginButtonSpacing

            )

        )



        // LOGIN BUTTON

        Button(

            text = "Log In",

            onClick = {


            }

        )



        Spacer(

            modifier = Modifier.height(

                AppDimensions.ForgotPasswordSpacing

            )

        )



        // FORGOT PASSWORD

        Text(

            text = "Forgot Password?",

            color = ForgotPasswordBlue,

            fontSize = AppTypography.ForgotPasswordTextSize,

            fontWeight = FontWeight.Medium

        )



        Spacer(

            modifier = Modifier.height(

                AppDimensions.OrSectionSpacing

            )

        )



        // OR SECTION

        androidx.compose.foundation.layout.Row(

            modifier = Modifier.fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically

        ) {


            HorizontalDivider(

                modifier = Modifier.weight(1f),

                color = LoginDividerColor

            )



            Text(

                text = "OR",

                color = LoginWhiteText,

                fontSize = AppTypography.OrTextSize,

                modifier = Modifier.padding(

                    horizontal = AppDimensions.PaddingMedium

                ),

                fontWeight = FontWeight.Medium

            )



            HorizontalDivider(

                modifier = Modifier.weight(1f),

                color = LoginDividerColor

            )

        }



        Spacer(

            modifier = Modifier.height(

                AppDimensions.GoogleButtonSpacing

            )

        )
        // CONTINUE WITH GOOGLE


        OutlinedButton(

            onClick = {


            },


            modifier = Modifier
                .fillMaxWidth()
                .height(

                    AppDimensions.SocialButtonHeight

                ),


            shape = RoundedCornerShape(

                AppDimensions.SocialButtonRadius

            ),


            border = BorderStroke(

                1.dp,

                GoogleButtonBorderColor

            )

        ) {


            Image(

                painter = painterResource(

                    id = R.drawable.google_ic

                ),

                contentDescription = "Google",

                modifier = Modifier
                    .height(

                        AppDimensions.GoogleIconSize

                    )
                    .width(

                        AppDimensions.GoogleIconSize

                    )

            )



            Spacer(

                modifier = Modifier.width(

                    AppDimensions.GoogleIconTextSpacing

                )

            )



            Text(

                text = "Continue with Google",

                color = LoginWhiteText,

                fontSize = AppTypography.SocialButtonTextSize

            )


        }



        Spacer(

            modifier = Modifier.height(

                AppDimensions.AccountButtonSpacing

            )

        )



        // CREATE ACCOUNT


        OutlinedButton(

            onClick = {


            },


            modifier = Modifier
                .fillMaxWidth()
                .height(

                    AppDimensions.SocialButtonHeight

                ),


            shape = RoundedCornerShape(

                AppDimensions.SocialButtonRadius

            ),


            border = BorderStroke(

                1.dp,

                CreateAccountColor

            )

        ) {



            Text(

                text = "Create new account",

                color = CreateAccountColor,

                fontSize = AppTypography.SocialButtonTextSize,

                fontWeight = FontWeight.Medium

            )


        }



    }

}






@Preview(

    showBackground = true,

    showSystemUi = true,

    device = "spec:width=412dp,height=715dp,dpi=420"

)

@Composable
fun LoginScreenPreview() {


    NavHighTheme {


        LoginScreen()


    }

}