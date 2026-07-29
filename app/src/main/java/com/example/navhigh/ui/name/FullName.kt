package com.example.navhigh.ui.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.common.textfield.FullNameTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.FullNameDescriptionAlpha
import com.example.navhigh.ui.theme.FullNameDescriptionColor
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme

@Composable
fun FullNameScreen(
    onBackClick: () -> Unit,
    onNextClick: (String) -> Unit,
    onLoginClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    // Used to dismiss the keyboard programmatically when Next is tapped
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(AppDimensions.TopSpace))

            BackArrow(onClick = onBackClick)

            Spacer(modifier = Modifier.height(AppDimensions.EmailBackArrowSpacing))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = stringResource(R.string.fullname_title))
                    )
                )
            )

            Spacer(modifier = Modifier.height(AppDimensions.EmailDescriptionSpacing))

            Text(
                text = stringResource(R.string.fullname_description),
                color = FullNameDescriptionColor.copy(alpha = FullNameDescriptionAlpha),
                fontSize = AppTypography.EmailDescriptionSize
            )

            Spacer(modifier = Modifier.height(AppDimensions.EmailTextFieldSpacing))

            FullNameTextField(
                value = fullName,
                onValueChange = { fullName = it }
            )

            Spacer(modifier = Modifier.height(AppDimensions.EmailButtonSpacing))

            Button(
                text = stringResource(R.string.fullname_next_button),
                onClick = {
                    // Dismiss the keyboard as soon as Next is tapped
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    if (fullName.trim().isNotEmpty()) {
                        onNextClick(fullName.trim())
                    }
                }
            )
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
                onContinue = onContinueClick
            )
        }
    }
}

// ---------------- PHONE PREVIEW ----------------

@Preview(showBackground = true, showSystemUi = true, name = "Phone Preview")
@Composable
fun FullNameScreenPhonePreview() {
    NavHighTheme {
        FullNameScreen(
            onBackClick = {},
            onNextClick = {},
            onLoginClick = {},
            onContinueClick = {}
        )
    }
}

// ---------------- TABLET PREVIEW ----------------

@Preview(showBackground = true, showSystemUi = true, name = "Tablet Preview", device = "spec:width=800dp,height=1280dp,dpi=240")
@Composable
fun FullNameScreenTabletPreview() {
    NavHighTheme {
        FullNameScreen(
            onBackClick = {},
            onNextClick = {},
            onLoginClick = {},
            onContinueClick = {}
        )
    }
}