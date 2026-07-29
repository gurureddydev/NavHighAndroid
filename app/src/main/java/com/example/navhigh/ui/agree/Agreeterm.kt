package com.example.navhigh.ui.agree

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.EmailDescriptionColor
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.FullWeight
import com.example.navhigh.ui.theme.TermsAgreementTitleColor

@Composable
fun TermsAgreementScreen(
    onBackClick: () -> Unit = {},
    onLearnMoreClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onCookiesPolicyClick: () -> Unit = {},
    onAgreeClick: () -> Unit = {},
    onLogin: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .widthIn(max = AppDimensions.PasswordTabletContentWidth)
                .fillMaxWidth()
                .padding(horizontal = AppDimensions.ScreenPadding)
                .navigationBarsPadding()
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(AppDimensions.TopSpace))
            BackArrow(onClick = { onBackClick() })

            Spacer(modifier = Modifier.height(AppDimensions.TermsAgreementBackArrowSpacing))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = "Agree to NavHigh's", color = TermsAgreementTitleColor)
                    ),
                    listOf(
                        TitlePart(text = "terms and policies", color = TermsAgreementTitleColor)
                    )
                )
            )

            Spacer(modifier = Modifier.height(AppDimensions.TermsAgreementSectionSpacing))

            // Paragraph 1: plain text EmailDescriptionColor, "Learn more" ForgotPasswordBlue
            val learnMoreText = buildAnnotatedString {
                withStyle(ParagraphStyle(lineHeight = AppTypography.lineheight)) {
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("People who use our service may have uploaded your contact information to NavHigh. ")
                    }
                    pushStringAnnotation(tag = "learn_more", annotation = "learn_more")
                    withStyle(
                        SpanStyle(
                            color = ForgotPasswordBlue,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("Learn more")
                    }
                    pop()
                }
            }
            ClickableText(
                text = learnMoreText,
                onClick = { offset ->
                    learnMoreText.getStringAnnotations(tag = "learn_more", start = offset, end = offset)
                        .firstOrNull()?.let { onLearnMoreClick() }
                }
            )

            Spacer(modifier = Modifier.height(AppDimensions.TermsAgreementSectionSpacing))

            // Paragraph 2: plain text EmailDescriptionColor, "I agree" bold TermsAgreementTitleColor,
            // "Terms" / "Privacy Policy" / "Cookies Policy" ForgotPasswordBlue
            val agreeText = buildAnnotatedString {
                withStyle(ParagraphStyle(lineHeight = AppTypography.lineheight)) {
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("By tapping ")
                    }
                    withStyle(
                        SpanStyle(
                            color = TermsAgreementTitleColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("I agree")
                    }
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append(", you agree to create an account and to NavHigh's ")
                    }
                    pushStringAnnotation(tag = "terms", annotation = "terms")
                    withStyle(
                        SpanStyle(
                            color = ForgotPasswordBlue,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("Terms")
                    }
                    pop()
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append(", ")
                    }
                    pushStringAnnotation(tag = "privacy", annotation = "privacy")
                    withStyle(
                        SpanStyle(
                            color = ForgotPasswordBlue,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("Privacy Policy")
                    }
                    pop()
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append(" and ")
                    }
                    pushStringAnnotation(tag = "cookies", annotation = "cookies")
                    withStyle(
                        SpanStyle(
                            color = ForgotPasswordBlue,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("Cookies Policy")
                    }
                    pop()
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append(".")
                    }
                }
            }
            ClickableText(
                text = agreeText,
                onClick = { offset ->
                    agreeText.getStringAnnotations(tag = "terms", start = offset, end = offset)
                        .firstOrNull()?.let { onTermsClick() }
                    agreeText.getStringAnnotations(tag = "privacy", start = offset, end = offset)
                        .firstOrNull()?.let { onPrivacyPolicyClick() }
                    agreeText.getStringAnnotations(tag = "cookies", start = offset, end = offset)
                        .firstOrNull()?.let { onCookiesPolicyClick() }
                }
            )

            Spacer(modifier = Modifier.height(AppDimensions.TermsAgreementSectionSpacing))

            // Paragraph 3: "Privacy Policy" ForgotPasswordBlue, rest EmailDescriptionColor
            val privacyDescText = buildAnnotatedString {
                withStyle(ParagraphStyle(lineHeight =AppTypography.lineheight)) {
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("The ")
                    }
                    pushStringAnnotation(tag = "privacy2", annotation = "privacy2")
                    withStyle(
                        SpanStyle(
                            color = ForgotPasswordBlue,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append("Privacy Policy")
                    }
                    pop()
                    withStyle(
                        SpanStyle(
                            color = EmailDescriptionColor,
                            fontSize = AppTypography.TermsAgreementBodyTextSize
                        )
                    ) {
                        append(" describes the ways we can use the information we collect when you create a profile. For example, we use this information to provide, personalize and improve our products, including ads.")
                    }
                }
            }
            ClickableText(
                text = privacyDescText,
                onClick = { offset ->
                    privacyDescText.getStringAnnotations(tag = "privacy2", start = offset, end = offset)
                        .firstOrNull()?.let { onPrivacyPolicyClick() }
                }
            )
            Spacer(modifier = Modifier.height(40.dp))


            Button(
                text = "I agree",
                onClick = onAgreeClick,
                modifier = Modifier.widthIn(max = 340.dp)
            )

        }

        // Bottom Already Have Account — pinned to bottom of the screen
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 7.dp)
        ) {
            AlreadyHaveAccount(
                onLogin = onLogin,
                onContinue = onContinue
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Preview",
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun TermsAgreementScreenPhonePreview() {
    NavHighTheme {
        TermsAgreementScreen()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet Preview",
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun TermsAgreementScreenTabletPreview() {
    NavHighTheme {
        TermsAgreementScreen()
    }
}