package com.example.navhigh.ui.bottombar

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.R
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.BottomNavBackground
import com.example.navhigh.ui.theme.BottomNavColorAnimationDurationMs
import com.example.navhigh.ui.theme.BottomNavDividerColor
import com.example.navhigh.ui.theme.BottomNavIconCrossfadeDurationMs
import com.example.navhigh.ui.theme.BottomNavSelectedColor
import com.example.navhigh.ui.theme.BottomNavUnselectedColor
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.FullWeight

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onRouteSelected: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BottomNavBackground)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {

        HorizontalDivider(
            thickness = AppDimensions.BottomNavigationDividerThickness,
            color = BottomNavDividerColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimensions.BottomNavigationHeight)
                .padding(horizontal = AppDimensions.BottomNavigationHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            NavItem(
                modifier = Modifier.weight(FullWeight),
                label = stringResource(R.string.nav_home),
                selected = currentRoute == "Home",
                iconRes = R.drawable.home,
                onClick = { onRouteSelected("Home") }
            )

            NavItem(
                modifier = Modifier.weight(FullWeight),
                label = stringResource(R.string.nav_discover),
                selected = currentRoute == "Search",
                iconRes = R.drawable.search1,
                onClick = { onRouteSelected("Search") }
            )

            NavItem(
                modifier = Modifier.weight(FullWeight),
                label = stringResource(R.string.nav_create),
                selected = currentRoute == "Create",
                iconRes = R.drawable.plus,
                onClick = { onRouteSelected("Create") }
            )

            NavItem(
                modifier = Modifier.weight(FullWeight),
                label = stringResource(R.string.nav_notifications),
                selected = currentRoute == "Notifications",
                iconRes = R.drawable.bell1,
                showDot = true,
                onClick = { onRouteSelected("Notifications") }
            )

            NavItem(
                modifier = Modifier.weight(FullWeight),
                label = stringResource(R.string.nav_profile),
                selected = currentRoute == "Profile",
                iconRes = R.drawable.profile,
                onClick = { onRouteSelected("Profile") }
            )

        }

    }

}

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    iconOutlined: ImageVector? = null,
    iconFilled: ImageVector? = null,
    iconRes: Int? = null,
    showDot: Boolean = false,
    onClick: () -> Unit
) {

    val animatedContentColor by animateColorAsState(
        targetValue = if (selected) BottomNavSelectedColor else BottomNavUnselectedColor,
        animationSpec = tween(BottomNavColorAnimationDurationMs),
        label = "Color"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        BadgedBox(
            badge = {
                if (showDot) {
                    Badge(
                        containerColor = ForgotPasswordBlue
                    )
                }
            }
        ) {

            if (iconRes != null) {

                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(animatedContentColor),
                    modifier = Modifier.size(AppDimensions.BottomNavigationIconSize)
                )

            } else {

                Crossfade(
                    targetState = selected,
                    animationSpec = tween(BottomNavIconCrossfadeDurationMs),
                    label = "Icon"
                ) {
                    Icon(
                        imageVector = if (it) iconFilled!! else iconOutlined!!,
                        contentDescription = label,
                        tint = animatedContentColor,
                        modifier = Modifier.size(AppDimensions.BottomNavigationIconSize)
                    )
                }

            }

        }

        Spacer(modifier = Modifier.height(AppDimensions.BottomNavigationItemSpacing))

        Text(
            text = label,
            color = animatedContentColor,
            fontWeight = FontWeight.Medium,
            style = LocalTextStyle.current.copy(
                fontSize = AppTypography.BottomNavigationLabelSize,
                lineHeight = AppTypography.BottomNavigationLabelSize,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                )
            )
        )

    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF020817
)
@Composable
fun BottomNavigationBarPreview() {

    var currentRoute by remember { mutableStateOf("Home") }

    BottomNavigationBar(
        currentRoute = currentRoute,
        onRouteSelected = { newRoute ->
            currentRoute = newRoute
        }
    )

}