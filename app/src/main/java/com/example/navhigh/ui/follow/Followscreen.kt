package com.example.navhigh.ui.follow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

/**
 * Data for one row in the suggested-people list.
 * [avatar] is optional — pass a loaded ImageBitmap (e.g. from Coil/your image
 * pipeline) when available; falls back to a plain colored circle otherwise.
 */
data class SuggestedUser(
    val id: String,
    val displayName: String,
    val username: String,
    val avatar: ImageBitmap? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowSuggestionsScreen(
    users: List<SuggestedUser>,
    onBackClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    onFollowClick: (Set<String>) -> Unit = {}
) {

    var searchQuery by remember { mutableStateOf("") }

    // All users start pre-selected, matching the screenshot
    var selectedIds by remember {
        mutableStateOf(users.map { it.id }.toSet())
    }

    val filteredUsers = remember(searchQuery, users) {
        if (searchQuery.isBlank()) {
            users
        } else {
            users.filter {
                it.displayName.contains(searchQuery, ignoreCase = true) ||
                        it.username.contains(searchQuery, ignoreCase = true)
            }
        }
    }

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
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(AppDimensions.TopSpace))

            // Back arrow (left) + Skip (right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackArrow(onClick = onBackClick)

                Text(
                    text = "Skip",
                    color = ForgotPasswordBlue,
                    fontSize = AppTypography.contactpermissionsize,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onSkipClick
                    )
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.followscreen))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = "Follow "),
                        TitlePart(text = "5 or more", color = ForgotPasswordBlue)
                    ),
                    listOf(
                        TitlePart(text = "people")
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search field — compact custom search bar (fixed 46dp height)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White.copy(alpha = 0.06f))
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = AppTypography.contactpermissionsize
                        )
                    }
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = Color.White,
                            fontSize = AppTypography.contactpermissionsize
                        ),
                        cursorBrush = androidx.compose.ui.graphics.SolidColor(ForgotPasswordBlue),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Suggested people list
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(filteredUsers, key = { it.id }) { user ->
                    SuggestedUserRow(
                        user = user,
                        selected = selectedIds.contains(user.id),
                        onToggle = {
                            selectedIds = if (selectedIds.contains(user.id)) {
                                selectedIds - user.id
                            } else {
                                selectedIds + user.id
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            Text(
                text = "Following isn't required, but it's recommended for a personalised experience.",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = AppTypography.EmailDescriptionSize,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                text = "Follow",
                isLoading = false,
                onClick = { onFollowClick(selectedIds) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SuggestedUserRow(
    user: SuggestedUser,
    selected: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onToggle
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Avatar
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            if (user.avatar != null) {
                Image(
                    bitmap = user.avatar,
                    contentDescription = user.displayName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                )
            } else {
                Text(
                    text = user.displayName.take(1).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = user.displayName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = AppTypography.EmailDescriptionSize,
                lineHeight = AppTypography.EmailDescriptionSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = user.username,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = AppTypography.contactpermissionsize,
                lineHeight = AppTypography.contactpermissionsize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Real Material3 checkbox instead of a manually drawn box
        Checkbox(
            checked = selected,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = ForgotPasswordBlue,
                uncheckedColor = Color.White.copy(alpha = 0.35f),
                checkmarkColor = Color.White
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
fun FollowSuggestionsScreenPreview() {
    NavHighTheme {
        FollowSuggestionsScreen(
            users = listOf(
                SuggestedUser(id = "1", displayName = "Sai lakshmi Narayana", username = "karanam_sai_26"),
                SuggestedUser(id = "2", displayName = "k.sravan", username = "k_sravan_kumar_"),
                SuggestedUser(id = "3", displayName = "Sai Reddy", username = "iam_saikumar_reddy"),
                SuggestedUser(id = "4", displayName = "tharun chowdary", username = "tharun_chowdary_15"),
                SuggestedUser(id = "5", displayName = "EasyhomeShiftingbangalore", username = "ehomeshifting"),
                SuggestedUser(id = "6", displayName = "T Nitish Timpuraram", username = "nitish_t")
            )
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF020817,
    name = "Suggested User Row"
)
@Composable
fun SuggestedUserRowPreview() {
    NavHighTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SuggestedUserRow(
                user = SuggestedUser(
                    id = "1",
                    displayName = "Sai lakshmi Narayana",
                    username = "karanam_sai_26"
                ),
                selected = true,
                onToggle = {}
            )
        }
    }
}