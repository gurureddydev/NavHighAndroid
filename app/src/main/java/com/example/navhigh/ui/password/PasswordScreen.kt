package com.example.navhigh.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.navhigh.common.dialoguebox.AlreadyHaveAccountDialog
import com.example.navhigh.common.textfield.PasswordTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.Background
import com.example.navhigh.ui.theme.BlueEnd
import com.example.navhigh.ui.theme.BorderBlue
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.FullWeight
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.PasswordDescriptionColor
import com.example.navhigh.ui.theme.White

@Composable
fun PasswordScreen(
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {},   // Goes to BirthdayScreen
    onLoginClick: () -> Unit = {}
) {

    var password by remember { mutableStateOf("") }
    var rememberLogin by remember { mutableStateOf(false) }
    var showLoginDialog by remember { mutableStateOf(false) }

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
            .background(Background)
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

            Spacer(modifier = Modifier.height(AppDimensions.BackToTitle))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = stringResource(R.string.password_title_part1)),
                        TitlePart(
                            text = stringResource(R.string.password_title_part2),
                            color = ForgotPasswordBlue
                        )
                    )
                )
            )

            Spacer(modifier = Modifier.height(AppDimensions.TitleToDescription))

            Text(
                text = stringResource(R.string.password_description),
                color = PasswordDescriptionColor,
                fontSize = AppTypography.Description,
                lineHeight = AppTypography.EmailDescriptionLineHeight
            )

            Spacer(modifier = Modifier.height(AppDimensions.DescriptionToTextField))

            PasswordTextField(
                value = password,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(AppDimensions.TextFieldToRemember))

            Row(verticalAlignment = Alignment.CenterVertically) {

                CustomCheckbox(
                    checked = rememberLogin,
                    onCheckedChange = { rememberLogin = it }
                )

                Spacer(modifier = Modifier.width(AppDimensions.RememberGap))

                Text(
                    text = stringResource(R.string.password_remember_text),
                    color = White,
                    fontSize = AppTypography.RememberText
                )

                Spacer(modifier = Modifier.width(AppDimensions.RememberTextSpacing))

                Text(
                    text = stringResource(R.string.password_learn_more),
                    color = BlueEnd,
                    fontSize = AppTypography.RememberText
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.RememberToButton))

            Button(
                text = stringResource(R.string.password_next_button),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    // Dismiss the keyboard as soon as Next is tapped
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    // Navigate to BirthdayScreen
                    onNextClick()
                }
            )

            Spacer(modifier = Modifier.weight(FullWeight))
        }

        // Bottom fixed "I already have an account"
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .then(
                    if (isTablet) {
                        Modifier.width(contentWidth)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
                // .navigationBarsPadding()
                .padding(
                    horizontal = AppDimensions.ScreenPadding,
                    vertical = AppDimensions.PasswordBottomVerticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AlreadyHaveAccount(
                onLogin = { showLoginDialog = true },
                onContinue = {}
            )
        }
    }

    if (showLoginDialog) {

        AlreadyHaveAccountDialog(
            onDismiss = { showLoginDialog = false },
            onContinue = { showLoginDialog = false },
            onLogin = {
                showLoginDialog = false
                onLoginClick()
            }
        )
    }
}

// ---------------- CUSTOM CHECKBOX ----------------

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier
            .size(AppDimensions.CheckboxSize)
            .clip(RoundedCornerShape(AppDimensions.CheckboxCornerRadius))
            .border(
                width = AppDimensions.CheckboxBorderWidth,
                color = BorderBlue,
                shape = RoundedCornerShape(AppDimensions.CheckboxCornerRadius)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {

        if (checked) {

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.password_checkbox_checked_description),
                tint = BorderBlue,
                modifier = Modifier.size(AppDimensions.CheckboxTickSize)
            )
        }
    }
}

// ---------------- PHONE PREVIEW ----------------

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun PasswordPhonePreview() {
    NavHighTheme {
        PasswordScreen()
    }
}

// ---------------- TABLET PREVIEW ----------------

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun PasswordTabletPreview() {
    NavHighTheme {
        PasswordScreen()
    }
}