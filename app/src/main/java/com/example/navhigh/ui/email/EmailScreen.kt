package com.example.navhigh.ui.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.textfield.EmailTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.EmailDescriptionColor
import com.example.navhigh.ui.theme.EmailLoadingDelayMs
import com.example.navhigh.ui.theme.EmailPattern
import com.example.navhigh.ui.theme.ErrorNeonRose
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme
import kotlinx.coroutines.delay
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.FullWeight

@Composable
fun EmailScreen(
    onNextClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    // Controller used to hide the keyboard programmatically when Next is tapped
    val keyboardController = LocalSoftwareKeyboardController.current

    val contentWidth: Dp =
        if (isTablet) AppDimensions.PasswordTabletContentWidth else Dp.Unspecified

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .then(
                    if (isTablet) {
                        Modifier.width(contentWidth)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
                .padding(horizontal = AppDimensions.ScreenPadding)
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(AppDimensions.TopSpace))
            BackArrow(onClick = { onBackClick() })

            Spacer(modifier = Modifier.height(AppDimensions.EmailBackArrowSpacing))
            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(
                            text = stringResource(R.string.email_title_line1)
                        ),
                        TitlePart(
                            text = stringResource(R.string.email_title_line2),
                            color = ForgotPasswordBlue
                        )
                    )
                )
            )

            Spacer(modifier = Modifier.height(AppDimensions.EmailDescriptionSpacing))

            Text(
                text = stringResource(R.string.email_description),
                color = EmailDescriptionColor,
                fontSize = AppTypography.EmailDescriptionSize,
                lineHeight = AppTypography.EmailDescriptionLineHeight
            )

            Spacer(modifier = Modifier.height(AppDimensions.EmailTextFieldSpacing))

            EmailTextField(
                value = email,
                isError = emailError,
                onValueChange = {
                    email = it
                    emailError = false
                }
            )

            if (emailError) {
                Spacer(modifier = Modifier.height(AppDimensions.EmailErrorSpacing))

                Text(
                    text = stringResource(R.string.email_error_text),
                    color = ErrorNeonRose,
                    fontSize = AppTypography.EmailDescriptionSize,
                    lineHeight = AppTypography.EmailDescriptionLineHeight
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.EmailButtonSpacing))

            Button(
                text = stringResource(R.string.email_next_button),
                isLoading = isLoading,
                onClick = {
                    // Dismiss the keyboard as soon as Next is tapped
                    keyboardController?.hide()

                    val emailPattern = Regex(EmailPattern)
                    if (email.matches(emailPattern)) {
                        emailError = false
                        isLoading = true
                    } else {
                        emailError = true
                    }
                }
            )

            Spacer(modifier = Modifier.weight(FullWeight))
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = AppDimensions.EmailScreenVerticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlreadyHaveAccount(
                onLogin = { onLoginClick() },
                onContinue = {}
            )
        }

        if (isLoading) {
            LaunchedEffect(isLoading) {
                delay(EmailLoadingDelayMs)
                isLoading = false
                onNextClick(email)
            }
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