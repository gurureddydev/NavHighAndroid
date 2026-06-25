package com.example.navhigh.ui.create

import PublishButton
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navhigh.ui.theme.DarkBackground

@Composable
fun CreateScreen(
    onCloseClick: () -> Unit,
    onDraftsClick: () -> Unit
) {
    CreateScreenContent(
        onCloseClick = onCloseClick,
        onDraftsClick = onDraftsClick
    )
}

@Composable
fun CreateScreenContent(
    onCloseClick: () -> Unit,
    onDraftsClick: () -> Unit
) {
    Surface(color = DarkBackground, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            CreatePostHeader(
                onCloseClick = onCloseClick,
                onDraftsClick = onDraftsClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                AddPhotoSection()
                // Assuming AudioScreen no longer needs an onSave callback
                AudioScreen()
                AddCaptionSection()
                AddHashtagsSection()
                PublishButton(onClick = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateScreen() {
    CreateScreenContent(
        onCloseClick = {},
        onDraftsClick = {}
    )
}