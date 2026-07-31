package com.example.navhigh.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.navhigh.R
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.FullWeight
import com.example.navhigh.ui.theme.NavHighTheme
import com.example.navhigh.ui.theme.PrimaryBlue

// ==========================================
// TOP HEADER LAYOUT
// ==========================================
@Composable
fun TopHeader(
    onSearchClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppDimensions.TopHeaderHorizontalPadding,
                vertical = AppDimensions.TopHeaderVerticalPadding
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.nav_high_logo),
                contentDescription = stringResource(id = R.string.top_header_nav_high_logo_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(AppDimensions.TopHeaderLogoSize)
                    .padding(bottom = AppDimensions.TopHeaderLogoBottomPadding)
            )

            Text(
                text = stringResource(id = R.string.top_header_title_nav),
                color = Color.White,
                fontSize = AppTypography.TopHeaderTitleTextSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.top_header_title_high),
                color = PrimaryBlue,
                fontSize = AppTypography.TopHeaderTitleTextSize,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(FullWeight))

        IconButton(onClick = onSearchClick) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = stringResource(id = R.string.top_header_search_description),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(AppDimensions.TopHeaderSearchIconSize)
            )
        }

        IconButton(onClick = onNotificationsClick) {
            BadgedBox(
                badge = {
                    Badge(containerColor = PrimaryBlue) {
                        Text(
                            text = stringResource(id = R.string.top_header_notification_badge_count),
                            color = Color.White,
                            fontSize = AppTypography.TopHeaderBadgeTextSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = stringResource(id = R.string.top_header_notifications_description),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(AppDimensions.TopHeaderSearchIconSize)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000) // 0xFF000000 is black
@Composable
fun PreviewTopHeader() {
    NavHighTheme {
        TopHeader(
            onSearchClick = { /* Handle preview click */ },
            onNotificationsClick = { /* Handle preview click */ }
        )
    }
}