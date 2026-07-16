package com.example.navhigh.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview

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
import com.example.navhigh.ui.theme.BorderBlue
import com.example.navhigh.ui.theme.BlueEnd
import com.example.navhigh.ui.theme.ForgotPasswordBlue
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

    var rememberLogin by remember {
        mutableStateOf(false)
    }

    var showLoginDialog by remember {
        mutableStateOf(false)
    }


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        val contentWidth = if (
            maxWidth > AppDimensions.TabletBreakpoint
        ) {
            AppDimensions.PasswordTabletContentMaxWidth
        } else {
            AppDimensions.PasswordContentMaxWidth
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = contentWidth)
                .align(Alignment.TopCenter)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = AppDimensions.ScreenPadding
                    )
            ) {


                Spacer(
                    modifier = Modifier.height(
                        AppDimensions.TopSpace
                    )
                )

                BackArrow(
                    onClick = {
                        onBackClick()
                    }
                )

                Spacer(
                    modifier = Modifier.height(
                        AppDimensions.BackToTitle
                    )
                )

                ScreenTitle(
                    lines = listOf(
                        listOf(
                            TitlePart(
                                text = "Create a "
                            ),
                            TitlePart(
                                text = "password",
                                color = ForgotPasswordBlue
                            )
                        )
                    )
                )

                Spacer(
                    modifier = Modifier.height(
                        AppDimensions.TitleToDescription
                    )
                )

                Text(
                    text = "Create a password with at least 6 letters\nor numbers. It should be something\nothers can't guess.",
                    color = PasswordDescriptionColor,
                    fontSize = AppTypography.Description,
                    lineHeight = AppTypography.EmailDescriptionLineHeight
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
                    }
                )

                Spacer(
                    modifier = Modifier.height(
                        AppDimensions.TextFieldToRemember
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

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

            }


            // Bottom fixed Already Have Account

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {

                AlreadyHaveAccount(
                    onLogin = {
                        showLoginDialog = true
                    },
                    onContinue = {

                    }
                )

            }

        }

    }


    if (showLoginDialog) {

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
// ---------------- CUSTOM CHECKBOX ----------------

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
    ) {

        if (checked) {

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = BorderBlue,
                modifier = Modifier.size(
                    AppDimensions.CheckboxTickSize
                )
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