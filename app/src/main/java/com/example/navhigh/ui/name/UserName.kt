package com.example.navhigh.ui.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.delay
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.common.textfield.UsernameTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.FullWeight
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.UserNameDescriptionAlpha
import com.example.navhigh.ui.theme.UserNameDescriptionColor
import com.example.navhigh.ui.theme.UserNameLoadingDelayMs

@Composable
fun UserNameScreen(
    fullName: String,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onLoginClick: () -> Unit,
    onContinueClick: () -> Unit

) {

    // Initial username without @
    val generatedUsername = remember(fullName) {
        fullName
            .lowercase()
            .replace(" ", "")
    }

    // TextField value
    var username by remember {
        mutableStateOf("@$generatedUsername")
    }

    // Controls blue tick
    var showTick by remember {
        mutableStateOf(true)
    }

    // Detect editing mode
    var isEditing by remember {
        mutableStateOf(false)
    }

    // Button loading
    var isLoading by remember {
        mutableStateOf(false)
    }

    // Used to dismiss the keyboard programmatically when Next is tapped
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isLoading) {
        if (isLoading) {

            delay(UserNameLoadingDelayMs)

            isLoading = false
            showTick = true
            onNextClick()
        }
    }

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    // Keeps tablet UI looking exactly like the phone UI
    val contentWidth: Dp =
        if (isTablet) AppDimensions.UserNameTabletContentWidth else Dp.Unspecified

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
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = AppDimensions.EmailScreenVerticalPadding
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
                            text = stringResource(R.string.username_title)
                        )
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(AppDimensions.UserNameTitleSpacing)
            )

            Text(
                text = stringResource(R.string.username_description),
                color = UserNameDescriptionColor.copy(alpha = UserNameDescriptionAlpha) ,
                fontSize = AppTypography.EmailDescriptionSize
            )

            Spacer(
                modifier = Modifier.height(AppDimensions.UserNameFieldSpacing)
            )
            UsernameTextField(
                value = username,

                onValueChange = {

                    username = it

                    // User started editing
                    isEditing = true

                    // Hide blue tick while editing
                    showTick = false
                },

                isAvailable = showTick,

                onFocusChanged = { focused ->

                    if (focused && !isEditing) {

                        username = username.removePrefix("@")

                        showTick = false
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(
                    AppDimensions.EmailButtonSpacing
                )
            )

            Button(

                text = if (isLoading) "" else stringResource(R.string.username_next_button),

                isLoading = isLoading,

                onClick = {

                    // Dismiss the keyboard as soon as Next is tapped
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    val finalUsername =
                        if (username.startsWith("@")) {
                            username
                        } else {
                            "@$username"
                        }

                    username = finalUsername

                    // Show blue tick
                    showTick = true

                    // Start loading
                    isLoading = true
                }
            )

            Spacer(
                modifier = Modifier.weight(FullWeight)
            )

        }
        // Bottom section
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .then(
                    if (isTablet) {
                        Modifier.width(contentWidth)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = AppDimensions.UserNameBottomVerticalPadding
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
    showSystemUi = true
)
@Composable
fun UserNamePreview() {

    NavHighTheme {

        UserNameScreen(
            fullName = "Poorna Prakash",
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
fun UserNameTabletPreview() {

    NavHighTheme {

        UserNameScreen(
            fullName = "Poorna Prakash",
            onBackClick = {},
            onNextClick = {},
            onLoginClick = {},
            onContinueClick = {}
        )
    }
}
