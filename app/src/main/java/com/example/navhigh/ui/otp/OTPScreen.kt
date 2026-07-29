package com.example.navhigh.ui.otp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.OtpDescriptionColor
import com.example.navhigh.ui.theme.OtpEmailHighlightColor
import com.example.navhigh.ui.theme.OtpErrorColor
import com.example.navhigh.ui.theme.OtpLength
import com.example.navhigh.ui.theme.OtpTextWhite
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    email: String,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onChangeEmailClick: () -> Unit = {},   // Navigates back to EmailScreen
    showBackArrow: Boolean = true
) {

    var otp by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var showResendSheet by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val contentWidth: Dp =
        if (isTablet) AppDimensions.OtpTabletContentMaxWidth else Dp.Unspecified

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
                .imePadding(),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(AppDimensions.TopSpace))
            BackArrow(onClick = { onBackClick() })

            Spacer(modifier = Modifier.height (AppDimensions.OtpBackArrowSpacing))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = stringResource(R.string.otp_title_line1))
                    ),
                    listOf(
                        TitlePart(text = stringResource(R.string.otp_title_line2_part1)),
                        TitlePart(
                            text = stringResource(R.string.otp_title_line2_part2),
                            color = ForgotPasswordBlue
                        )
                    )
                )
            )

            Spacer(modifier = Modifier.height(AppDimensions.OtpTitleSpacing))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = OtpDescriptionColor)) {
                        append(stringResource(R.string.otp_description))
                    }
                    append(" ")
                    withStyle(style = SpanStyle(color = OtpEmailHighlightColor)) {
                        append(email)
                    }
                },
                fontSize = AppTypography.EmailDescriptionSize
            )

            Spacer(modifier = Modifier.height(AppDimensions.OtpEmailTextSpacing))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { focusRequester.requestFocus() }
            ) {

                BasicTextField(
                    value = otp,
                    onValueChange = {
                        if (it.length <= OtpLength) {
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
                        ) {

                            repeat(OtpLength) { index ->

                                val value =
                                    if (index < otp.length)
                                        otp[index].toString()
                                    else
                                        ""

                                Box(
                                    modifier = Modifier
                                        .width(AppDimensions.OtpBoxWidth)
                                        .height(AppDimensions.OtpBoxHeight)
                                        .border(
                                            width = AppDimensions.OtpBorderWidth,
                                            color = if (isError)
                                                OtpErrorColor
                                            else
                                                ForgotPasswordBlue,
                                            shape = RoundedCornerShape(
                                                AppDimensions.OtpBoxRadius
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = value,
                                        color = OtpTextWhite,
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
                    text = stringResource(R.string.otp_error_text),
                    color = OtpErrorColor,
                    fontSize = AppTypography.EmailDescriptionSize,
                    modifier = Modifier.padding(
                        top = AppDimensions.OtpErrorTextPadding
                    )
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.OtpButtonSpacing))

            Button(
                text = stringResource(R.string.otp_next_button),
                onClick = {
                    // Dismiss the keyboard as soon as Next is tapped, regardless of validity
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    if (otp.length == OtpLength) {
                        onNextClick()
                    } else {
                        isError = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(AppDimensions.OtpResendButtonSpacing))

            OutlinedButton(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    showResendSheet = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.OtpResendButtonHeight),
                shape = RoundedCornerShape(AppDimensions.OtpResendButtonRadius),
                border = BorderStroke(
                    AppDimensions.OtpBorderWidth,
                    ForgotPasswordBlue
                )
            ) {

                Text(
                    text = stringResource(R.string.otp_resend_button),
                    color = OtpTextWhite,
                    fontSize = AppTypography.EmailDescriptionSize
                )
            }
        }


        if (showResendSheet) {

            ModalBottomSheet(
                onDismissRequest = { showResendSheet = false },
                containerColor = LoginBackground,
                shape = RoundedCornerShape(
                    topStart = AppDimensions.OtpSheetRadius,
                    topEnd = AppDimensions.OtpSheetRadius
                )

            ) {

                Column(
                    modifier = Modifier
                        .widthIn(max = AppDimensions.OtpSheetMaxWidth)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(AppDimensions.OtpSheetPadding)

                ) {

                    Text(
                        text = stringResource(R.string.otp_sheet_close),
                        color = OtpTextWhite,
                        fontSize = AppTypography.EmailTitleSize,
                        modifier = Modifier.clickable { showResendSheet = false }
                    )

                    Spacer(modifier = Modifier.height(AppDimensions.OtpSheetTopSpacing))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = AppDimensions.OtpBorderWidth,
                                color = ForgotPasswordBlue,
                                shape = RoundedCornerShape(AppDimensions.OtpSheetBoxRadius)
                            )

                    ) {

                        Text(
                            text = stringResource(R.string.otp_sheet_resend_option),
                            color = OtpTextWhite,
                            fontSize = AppTypography.EmailDescriptionSize,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimensions.OtpSheetItemPadding)
                                .clickable {
                                    showResendSheet = false
                                    // TODO: trigger resend OTP logic here
                                }
                        )

                        HorizontalDivider(color = ForgotPasswordBlue)

                        Text(
                            text = stringResource(R.string.otp_sheet_change_email_option),
                            color = OtpTextWhite,
                            fontSize = AppTypography.EmailDescriptionSize,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimensions.OtpSheetItemPadding)
                                .clickable {
                                    showResendSheet = false
                                    onChangeEmailClick()   // Navigate to EmailScreen
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(AppDimensions.OtpSheetBottomSpacing))

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
                .widthIn(max = AppDimensions.OtpSheetMaxWidth)
                .background(
                    LoginBackground,
                    RoundedCornerShape(
                        topStart = AppDimensions.OtpSheetRadius,
                        topEnd = AppDimensions.OtpSheetRadius
                    )
                )
                .padding(AppDimensions.OtpSheetPadding)

        ) {

            Text(
                text = stringResource(R.string.otp_sheet_close),
                color = OtpTextWhite,
                fontSize = AppTypography.EmailTitleSize
            )

            Spacer(modifier = Modifier.height(AppDimensions.OtpSheetTopSpacing))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = AppDimensions.OtpBorderWidth,
                        color = ForgotPasswordBlue,
                        shape = RoundedCornerShape(AppDimensions.OtpSheetBoxRadius)
                    )
            ) {

                Text(
                    text = stringResource(R.string.otp_sheet_resend_option),
                    color = OtpTextWhite,
                    fontSize = AppTypography.EmailDescriptionSize,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimensions.OtpSheetItemPadding)
                )

                HorizontalDivider(color = ForgotPasswordBlue)

                Text(
                    text = stringResource(R.string.otp_sheet_change_email_option),
                    color = OtpTextWhite,
                    fontSize = AppTypography.EmailDescriptionSize,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimensions.OtpSheetItemPadding)
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.OtpSheetBottomSpacing))
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
fun OtpPhonePreview() {

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
fun OtpTabletPreview() {

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
fun OtpBottomSheetPreview() {

    NavHighTheme {

        OtpBottomSheetPreviewContent()

    }
}