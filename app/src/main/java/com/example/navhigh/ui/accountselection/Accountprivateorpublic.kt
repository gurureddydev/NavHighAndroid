package com.example.navhigh.ui.accountselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme

enum class AccountPrivacy {
    PRIVATE,
    PUBLIC
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPrivacyScreen(
    onBackClick: () -> Unit = {},
    onNextClick: (AccountPrivacy) -> Unit = {}
) {

    var selectedPrivacy by remember { mutableStateOf(AccountPrivacy.PRIVATE) }

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

            Spacer(modifier = Modifier.height(AppDimensions.accountselectionscreeenspacing))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = "Set your account")
                    ),
                    listOf(
                        TitlePart(text = "privacy")
                    )
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            PrivacyOptionRow(
                icon = Icons.Filled.Lock,
                title = "Private",
                description = "Only the followers that you confirm on NavHigh can see what you share.",
                selected = selectedPrivacy == AccountPrivacy.PRIVATE,
                onSelect = { selectedPrivacy = AccountPrivacy.PRIVATE }
            )

            Spacer(modifier = Modifier.height(40.dp))

            PrivacyOptionRow(
                icon = Icons.Filled.LockOpen,
                title = "Public",
                description = "Anyone on or off NavHigh can see what you share.",
                selected = selectedPrivacy == AccountPrivacy.PUBLIC,
                onSelect = { selectedPrivacy = AccountPrivacy.PUBLIC }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "You can change this at any time in Settings.",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = AppTypography.EmailDescriptionSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center

            )

            Button(
                text = "Next",
                isLoading = false,
                onClick = { onNextClick(selectedPrivacy) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .widthIn(max = 340.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PrivacyOptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    // Blue when this option is selected, dimmed white/gray when it's not
    val iconTint = if (selected) ForgotPasswordBlue else Color.White.copy(alpha = 0.4f)
    val iconBackground = if (selected) {
        ForgotPasswordBlue.copy(alpha = 0.15f)
    } else {
        Color.White.copy(alpha = 0.08f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelect
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = AppTypography.contactpermissionsize
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = AppTypography.EmailDescriptionSize
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = ForgotPasswordBlue,
                unselectedColor = Color.White.copy(alpha = 0.4f)
            )
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Preview",
    device = "spec:width=412dp,height=915dp,dpi=420"
)
@Composable
fun AccountPrivacyScreenPhonePreview() {
    NavHighTheme {
        AccountPrivacyScreen()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet Preview",
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun AccountPrivacyScreenTabletPreview() {
    NavHighTheme {
        AccountPrivacyScreen()
    }
}
