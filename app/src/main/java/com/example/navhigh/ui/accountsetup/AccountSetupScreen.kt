package com.example.navhigh.ui.accountsetup

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme

// How long the spinner runs before the screen finishes and moves on
private const val AccountSetupDurationMs = 6000L

@Composable
fun AccountSetupScreen(
    onSetupFinished: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    // Waits exactly 6 seconds, then signals the caller (e.g. navigate to Home)
    LaunchedEffect(Unit) {
        delay(AccountSetupDurationMs.milliseconds)
        onSetupFinished()
    }

    // Continuous rotation for the spinner arc
    val infiniteTransition = rememberInfiniteTransition(label = "AccountSetupSpinner")
    val rotationDegrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing)
        ),
        label = "AccountSetupSpinnerRotation"
    )

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
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = AppDimensions.ContactsSyncTopVerticalPadding
                ),

        ) {

            Spacer(modifier = Modifier.height(20.dp))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = "Keep "),
                        TitlePart(text = "NavHigh", color = ForgotPasswordBlue),
                        TitlePart(text = " open to")
                    ),
                    listOf(
                        TitlePart(text ="finish setting up your")
                    ),
                    listOf(
                        TitlePart(text = "account")
                    )
                ),

            )

            Spacer(modifier = Modifier.height(AppDimensions.ContactsSyncTitleSpacing))

            Text(
                text = "Please wait while we set up your account.",
                color = Color(0xFFB6BAC4),
                fontSize = AppTypography.contactpermissionsize
            )
        }

        // Centered rotating loader
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(64.dp)
        ) {
            val strokeWidthPx = 5.dp.toPx()

            rotate(degrees = rotationDegrees) {
                drawArc(
                    brush = Brush.sweepGradient(
                        listOf(
                            Color.Transparent,
                            Color(0xFF00E5FF),
                            Color(0xFF2979FF),
                            Color.Transparent
                        )
                    ),
                    startAngle = 0f,
                    sweepAngle = 300f,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                    topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                    size = Size(
                        width = size.width - strokeWidthPx,
                        height = size.height - strokeWidthPx
                    )
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Preview"
)
@Composable
fun AccountSetupScreenPreview() {
    NavHighTheme {
        AccountSetupScreen(
            onSetupFinished = {}
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
fun AccountSetupScreenTabletPreview() {
    NavHighTheme {
        AccountSetupScreen(
            onSetupFinished = {}
        )
    }
}