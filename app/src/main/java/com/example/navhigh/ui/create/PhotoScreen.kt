package com.example.navhigh.ui.create

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.navhigh.R
import com.example.navhigh.ui.theme.DarkBackground
import com.example.navhigh.ui.theme.*

@Composable
fun AddPhotoSection(modifier: Modifier = Modifier) {
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var showFullImage by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedBitmap = bitmap
        selectedUri = null
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedUri = uri
        capturedBitmap = null
    }

    val stroke = Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f))
    val hasImage = capturedBitmap != null || selectedUri != null

    // Floating Full-screen Image Dialog with Zoom and Pan
    if (showFullImage) {
        Dialog(onDismissRequest = { showFullImage = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }
            val state = rememberTransformableState { zoomChange, panChange, _ ->
                scale = (scale * zoomChange).coerceIn(1f, 5f)
                offset += panChange
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f))
                    .clickable { showFullImage = false },
                contentAlignment = Alignment.Center
            ) {
                val imageModifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .transformable(state = state)

                if (capturedBitmap != null) {
                    Image(bitmap = capturedBitmap!!.asImageBitmap(), contentDescription = "Full", modifier = imageModifier, contentScale = ContentScale.Fit)
                } else if (selectedUri != null) {
                    AsyncImage(model = selectedUri, contentDescription = "Full", modifier = imageModifier, contentScale = ContentScale.Fit)
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        // Header with Delete
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("1. Add Photo", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            if (hasImage) {
                IconButton(onClick = { capturedBitmap = null; selectedUri = null }) {
                    Icon(Icons.Default.Delete, "Delete", tint = Color.Red)
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BoxBackground)
                .drawBehind { drawRect(color = BoxStrokeColor, style = stroke) }
                .clickable(enabled = hasImage) { showFullImage = true },
            contentAlignment = Alignment.Center
        ) {
            when {
                capturedBitmap != null -> {
                    Image(
                        bitmap = capturedBitmap!!.asImageBitmap(),
                        contentDescription = "Captured",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                selectedUri != null -> {
                    AsyncImage(
                        model = selectedUri,
                        contentDescription = "Selected",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                else -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(modifier = Modifier.size(60.dp).background(Color(0xFF1E293B), RoundedCornerShape(30.dp)), contentAlignment = Alignment.Center) {
                            Icon(painter = painterResource(id = R.drawable.camera), contentDescription = null, tint = IconBlue, modifier = Modifier.size(28.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Add a photo", color = LabelColor, fontWeight = FontWeight.SemiBold)
                        Text("JPG, PNG up to 10MB", color = SubtextColor, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { cameraLauncher.launch(null) },
                modifier = Modifier.weight(1f).height(35.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, IconBlue)
            ) {
                Icon(painter = painterResource(id = R.drawable.camera), null, tint = IconBlue, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Capture", color = IconBlue, fontWeight = FontWeight.Medium)
            }
            OutlinedButton(
                onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                modifier = Modifier.weight(1f).height(35.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, IconBlue)
            ) {
                Icon(painter = painterResource(id = R.drawable.gallery), null, tint = IconBlue, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Gallery", color = IconBlue, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoSection() {
    Surface(color = DarkBackground, modifier = Modifier.fillMaxSize()) {
        AddPhotoSection()
    }
}