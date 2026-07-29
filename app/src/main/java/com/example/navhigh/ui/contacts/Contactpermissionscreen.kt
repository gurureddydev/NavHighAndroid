package com.example.navhigh.ui.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ContactsSyncBodyColor
import com.example.navhigh.ui.theme.ContactsSyncIconColor
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LearnMoreLinkColor
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.SkipTextColor

@Composable
fun ContactsSyncScreen(
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
    onLearnMoreClick: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val contentWidth: Dp =
        if (isTablet) AppDimensions.ContactsSyncTabletContentWidth else Dp.Unspecified

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
    ) {

        // Top section
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
                    vertical = AppDimensions.ContactsSyncTopVerticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(
                            text = stringResource(R.string.contacts_sync_title_line1)
                        )
                    ),
                    listOf(
                        TitlePart(
                            text = stringResource(R.string.contacts_sync_title_line2),
                            color = ForgotPasswordBlue
                        )
                    )
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppDimensions.ContactsSyncTitleSpacing))

            ContactsSyncInfoRow(
                icon = Icons.Filled.Refresh,
                text = stringResource(R.string.contacts_sync_body_sync)
            )

            Spacer(modifier = Modifier.height(AppDimensions.ContactsSyncRowSpacing))

            ContactsSyncInfoRowWithLink(
                icon = Icons.Filled.Settings,
                prefix = stringResource(R.string.contacts_sync_body_settings_prefix),
                linkText = stringResource(R.string.contacts_sync_learn_more),
                onLinkClick = onLearnMoreClick
            )
        }

        // Bottom section: gradient Next button + Skip text link
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
                    vertical = AppDimensions.ContactsSyncBottomVerticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                text = stringResource(R.string.contacts_sync_next_button),
                isLoading = false,
                onClick = onNextClick
            )

            Spacer(modifier = Modifier.height(AppDimensions.ContactsSyncSkipSpacing))

            val skipInteractionSource = remember { MutableInteractionSource() }

            Text(
                text = stringResource(R.string.contacts_sync_skip),
                color = SkipTextColor,
                fontSize = AppTypography.contactpermissionsize,
                modifier = Modifier.clickable(
                    interactionSource = skipInteractionSource,
                    indication = null,
                    onClick = onSkipClick
                )
            )
        }
    }
}

@Composable
private fun ContactsSyncInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ContactsSyncIconColor,
            modifier = Modifier
                .size(AppDimensions.ContactsSyncIconSize)
                .padding(top = AppDimensions.ContactsSyncIconTopPadding)
        )

        Spacer(modifier = Modifier.width(AppDimensions.ContactsSyncIconTextSpacing))

        Text(
            text = text,
            color = ContactsSyncBodyColor,
            fontSize = AppTypography.contactpermissionsize
        )
    }
}

@Composable
private fun ContactsSyncInfoRowWithLink(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    prefix: String,
    linkText: String,
    onLinkClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ContactsSyncIconColor,
            modifier = Modifier
                .size(AppDimensions.ContactsSyncIconSize)
                .padding(top = AppDimensions.ContactsSyncIconTopPadding)
        )

        Spacer(modifier = Modifier.width(AppDimensions.ContactsSyncIconTextSpacing))

        val annotatedText = buildAnnotatedString {
            append("$prefix ")

            withLink(
                LinkAnnotation.Clickable(
                    tag = "LEARN_MORE",
                    styles = TextLinkStyles(
                        style = SpanStyle(color = LearnMoreLinkColor)
                    ),
                    linkInteractionListener = { onLinkClick() }
                )
            ) {
                append(linkText)
            }
        }

        Text(
            text = annotatedText,
            color = ContactsSyncBodyColor,
            fontSize = AppTypography.contactpermissionsize
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactsSyncScreenPreview() {
    NavHighTheme {
        ContactsSyncScreen(
            onNextClick = {},
            onSkipClick = {},
            onLearnMoreClick = {}
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
fun ContactsSyncScreenTabletPreview() {
    NavHighTheme {
        ContactsSyncScreen(
            onNextClick = {},
            onSkipClick = {},
            onLearnMoreClick = {}
        )
    }
}