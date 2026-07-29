package com.example.navhigh.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.NavBrand
import com.example.navhigh.common.textfield.PasswordTextField
import com.example.navhigh.common.textfield.UsernameOrEmailTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.CreateAccountColor
import com.example.navhigh.ui.theme.DialogBackground
import com.example.navhigh.ui.theme.DialogCreateRed
import com.example.navhigh.ui.theme.DialogMessageColor
import com.example.navhigh.ui.theme.DialogOkBlue
import com.example.navhigh.ui.theme.DialogTitleColor
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.FullWeight
import com.example.navhigh.ui.theme.GoogleButtonBorderColor
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.LoginDividerColor
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.TextGray

@Composable
fun LoginScreen(
    onCreateAccountClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLoginError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = AppDimensions.LoginContentMaxWidth)
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .verticalScroll(scrollState)
                .padding(
                    horizontal = AppDimensions.ScreenHorizontalPadding,
                    vertical = AppDimensions.ScreenVerticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            NavBrand(gradientTagline = true)

            Spacer(modifier = Modifier.height(AppDimensions.LogoToFieldSpacing))

            // USERNAME OR EMAIL FIELD
            UsernameOrEmailTextField(
                value = usernameOrEmail,
                onValueChange = { usernameOrEmail = it }
            )

            Spacer(modifier = Modifier.height(AppDimensions.FieldSpacing))

            // PASSWORD FIELD
            PasswordTextField(
                value = password,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(AppDimensions.LoginButtonSpacing))

            // LOGIN BUTTON
            Button(
                text = stringResource(R.string.login_button),
                onClick = {
                    if (usernameOrEmail.isBlank() || password.isBlank()) {
                        showLoginError = true
                    } else {
                        onLoginSuccess()
                    }
                }
            )

            Spacer(modifier = Modifier.height(AppDimensions.ForgotPasswordSpacing))

            Text(
                text = stringResource(R.string.login_forgot_password),
                color = ForgotPasswordBlue,
                fontSize = AppTypography.LoginForgotPasswordTextSize,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(AppDimensions.LoginOrSectionSpacing))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(FullWeight),
                    color = LoginDividerColor
                )

                Text(
                    text = stringResource(R.string.login_or_divider),
                    color = TextGray,
                    fontSize = AppTypography.LoginOrTextSize,
                    modifier = Modifier.padding(horizontal = AppDimensions.LoginPaddingMedium),
                    fontWeight = FontWeight.Medium
                )

                HorizontalDivider(
                    modifier = Modifier.weight(FullWeight),
                    color = LoginDividerColor
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.LoginGoogleButtonSpacing))

            // GOOGLE BUTTON
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.LoginSocialButtonHeight),
                shape = RoundedCornerShape(AppDimensions.LoginSocialButtonRadius),
                border = BorderStroke(AppDimensions.GoogleButtonBorderWidth, GoogleButtonBorderColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_ic),
                    contentDescription = stringResource(R.string.login_google_description),
                    modifier = Modifier.size(AppDimensions.LoginGoogleIconSize)
                )

                Spacer(modifier = Modifier.width(AppDimensions.LoginGoogleIconTextSpacing))

                Text(
                    text = stringResource(R.string.login_google_button),
                    color = TextGray,
                    fontSize = AppTypography.LoginSocialButtonTextSize
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.LoginAccountButtonSpacing))

            // CREATE ACCOUNT BUTTON
            OutlinedButton(
                onClick = { onCreateAccountClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.LoginSocialButtonHeight),
                shape = RoundedCornerShape(AppDimensions.LoginSocialButtonRadius),
                border = BorderStroke(AppDimensions.CreateAccountBorderWidth, CreateAccountColor)
            ) {
                Text(
                    text = stringResource(R.string.login_create_account_button),
                    color = CreateAccountColor,
                    fontSize = AppTypography.LoginSocialButtonTextSize,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.LoginBottomScrollSpacing))

            if (showLoginError) {
                AlertDialog(
                    onDismissRequest = { showLoginError = false },
                    containerColor = DialogBackground,
                    title = {
                        Text(
                            text = stringResource(R.string.login_error_title),
                            color = DialogTitleColor,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.login_error_message),
                            color = DialogMessageColor
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showLoginError = false }) {
                            Text(
                                text = stringResource(R.string.login_error_ok),
                                color = DialogOkBlue
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showLoginError = false
                                onCreateAccountClick()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.login_error_create_account),
                                color = DialogCreateRed
                            )
                        }
                    }
                )
            }
        }
    }
}

// ---------------- PREVIEW ----------------

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=320dp,height=715dp,dpi=420")
@Composable
fun LoginScreenPreview() {
    NavHighTheme {
        LoginScreen()
    }
}