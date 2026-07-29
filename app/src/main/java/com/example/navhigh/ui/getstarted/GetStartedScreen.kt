package com.example.navhigh.ui.getstarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.FullWeight
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme

@Composable
fun NavHighLogoText(
    modifier: Modifier = Modifier
) {
    Text(
        text = "NavHigh",
        color = Color.White,
        fontSize = 40.sp,
        fontFamily = FontFamily.Cursive,
        modifier = modifier
    )
}

@Composable
fun NavHighLogoScreen(
    fullName: String,
    onNextClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
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
                .navigationBarsPadding()
                .widthIn(max = AppDimensions.PasswordTabletContentWidth)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = AppDimensions.ScreenPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            NavHighLogoText()

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.nav_high_logo),
                contentDescription = "NavHigh logo image",
                modifier = Modifier.size(170.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Lets get started,\n$fullName",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Start customizing your experience",
                color = Color(0xFFB6BAC4),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            // Pushes the Next button (and AlreadyHaveAccount) down to the bottom of the screen,
            // same pattern used in TermsAgreementScreen
            Spacer(modifier = Modifier.weight(FullWeight))


            Button(
                text = "Next",
                onClick = onNextClick,
                modifier = Modifier.widthIn(max = 340.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            AlreadyHaveAccount(
                onLogin = { onLoginClick() },
                onContinue = onContinueClick
            )

            Spacer(modifier = Modifier.height(15.dp))
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
fun NavHighLogoScreenPhonePreview() {
    NavHighTheme {
        NavHighLogoScreen(fullName = "PoornaPrakashReddy_!")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet Preview",
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun NavHighLogoScreenTabletPreview() {
    NavHighTheme {
        NavHighLogoScreen(fullName = "PoornaPrakashReddy_!")
    }
}