package com.example.navhigh.ui.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.common.textfield.UsernameTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.LoginBackground

@Composable
fun UserNameScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onLoginClick: () -> Unit,
    onContinueClick: () -> Unit
) {

    var username by remember {
        mutableStateOf("t.poornaprakash")
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
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .fillMaxWidth()
                .widthIn(
                    max = AppDimensions.EmailContentMaxWidth
                )
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = if (isTablet)
                        60.dp
                    else
                        AppDimensions.EmailScreenVerticalPadding
                ),

            horizontalAlignment = Alignment.Start

        ) {

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
                            text = "Create a"
                        )

                    ),

                    listOf(

                        TitlePart(
                            text = "username"
                        )

                    )

                )
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            UsernameDescription(
                text = "Add a username or use our suggestion.\nYou can change this at any time."
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )
            UsernameTextField(
                value = username,
                onValueChange = {
                    username = it
                }
            )

            Spacer(
                modifier = Modifier.height(
                    AppDimensions.EmailButtonSpacing
                )
            )

            Button(
                text = "Next",
                onClick = {
                    if (username.trim().isNotEmpty()) {
                        onNextClick()
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
                .widthIn(
                    max = AppDimensions.EmailContentMaxWidth
                )
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = 24.dp
                ),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

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

@Composable
fun UsernameDescription(text: String) {
    TODO("Not yet implemented")
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Preview"
)
@Composable
fun UserNameScreenPhonePreview() {

    UserNameScreen(
        onBackClick = {},
        onNextClick = {},
        onLoginClick = {},
        onContinueClick = {}
    )

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet Preview",
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun UserNameScreenTabletPreview() {

    UserNameScreen(
        onBackClick = {},
        onNextClick = {},
        onLoginClick = {},
        onContinueClick = {}
    )

}