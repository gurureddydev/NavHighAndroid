package com.example.navhigh.ui.photo

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.LoginBackground
import com.example.navhigh.ui.theme.NavHighTheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    onAddPhotoClick: () -> Unit,
    onSkipClick: () -> Unit,
    onPhotoSelected: (Uri?) -> Unit = {}
) {

    val context = LocalContext.current
    val density = LocalDensity.current
    val isPreview = LocalInspectionMode.current

    // ---- Screen width (Preview-safe) ----
    // LocalWindowInfo.current.containerSize can be unreliable inside the
    // layout/Preview renderer, so we skip it entirely in Preview mode and
    // fall back to a fixed phone-sized width instead of crashing inflation.
    val screenWidthDp: Dp = if (isPreview) {
        360.dp
    } else {
        val windowInfo = LocalWindowInfo.current
        with(density) { windowInfo.containerSize.width.toDp() }
    }
    val isTablet = screenWidthDp >= 600.dp

    val contentWidth: Dp =
        if (isTablet) AppDimensions.PasswordTabletContentWidth else Dp.Unspecified

    // Currently selected image (either captured or picked from gallery)
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Bottom sheet visibility
    var showPickerSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // ---- Gallery launcher ----
    // Skipped entirely in Preview: rememberLauncherForActivityResult needs a
    // real ActivityResultRegistryOwner, which the Preview host doesn't provide,
    // and calling it unconditionally crashes the Preview render.
    val galleryLauncher: ManagedActivityResultLauncher<String, Uri?>? = if (!isPreview) {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                selectedImageBitmap = uriToBitmap(context, uri)
                onPhotoSelected(uri)
            }
        }
    } else {
        null
    }

    // ---- Camera launcher (writes full-res photo to a FileProvider uri) ----
    val cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>? = if (!isPreview) {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success: Boolean ->
            if (success && pendingCameraUri != null) {
                selectedImageBitmap = uriToBitmap(context, pendingCameraUri!!)
                onPhotoSelected(pendingCameraUri)
            }
        }
    } else {
        null
    }

    fun launchCamera() {
        val uri = createImageUri(context)
        pendingCameraUri = uri
        cameraLauncher?.launch(uri)
    }

    fun onTakePhotoClicked() {
        showPickerSheet = false
        launchCamera()
    }

    fun onChooseFromGalleryClicked() {
        showPickerSheet = false
        galleryLauncher?.launch("image/*")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
    ) {

        // Skip, top-right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .then(if (isPreview) Modifier else Modifier.statusBarsPadding())
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = AppDimensions.EmailScreenVerticalPadding
                )
        ) {

            val skipInteractionSource = remember { MutableInteractionSource() }

            Text(
                text = "Skip",
                color = ForgotPasswordBlue,
                fontSize = AppTypography.contactpermissionsize,
                modifier = Modifier.clickable(
                    interactionSource = skipInteractionSource,
                    indication = null,
                    onClick = onSkipClick
                )
            )
        }

        // Title, top-left, below the Skip row
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .then(if (isPreview) Modifier else Modifier.statusBarsPadding())
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
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(AppDimensions.EmailButtonSpacing))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = "Add a profile photo")
                    ),
                    listOf(
                        TitlePart(text = "that shows your vibe")
                    )
                )
            )
        }

        // Dashed circle with camera icon (or selected photo), centered
        PhotoPickerCircle(
            modifier = Modifier.align(Alignment.Center),
            selectedImageBitmap = selectedImageBitmap,
            onClick = { showPickerSheet = true }
        )

        // Bottom button
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .then(if (isPreview) Modifier else Modifier.navigationBarsPadding())
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
                text = "Add a photo",
                isLoading = false,
                onClick = {
                    if (selectedImageBitmap == null) {
                        showPickerSheet = true
                    } else {
                        onAddPhotoClick()
                    }
                }
            )
        }
    }

    // ---- Bottom sheet: Take a Photo / Choose from Gallery ----
    if (showPickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPickerSheet = false },
            sheetState = sheetState,
            containerColor = LoginBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                PickerOptionRow(
                    icon = Icons.Filled.CameraAlt,
                    label = "Take a Photo",
                    onClick = {
                        onTakePhotoClicked()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                PickerOptionRow(
                    icon = Icons.Filled.PhotoLibrary,
                    label = "Choose from Gallery",
                    onClick = {
                        onChooseFromGalleryClicked()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun PickerOptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ForgotPasswordBlue
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = AppTypography.EmailDescriptionSize
        )
    }
}

@Composable
private fun PhotoPickerCircle(
    modifier: Modifier = Modifier,
    selectedImageBitmap: Bitmap?,
    onClick: () -> Unit
) {

    val circleSize = 220.dp
    val circleInteractionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(circleSize)
            .clickable(
                interactionSource = circleInteractionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        // Dashed gradient ring
        Canvas(modifier = Modifier.size(circleSize)) {
            val strokeWidthPx = 2.dp.toPx()
            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF3D5CFF),
                        Color(0xFF00E5FF)
                    ),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, 0f)
                ),
                radius = size.minDimension / 2 - strokeWidthPx / 2,
                style = Stroke(
                    width = strokeWidthPx,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(18f, 14f),
                        phase = 0f
                    ),
                    cap = StrokeCap.Round
                )
            )
        }

        if (selectedImageBitmap != null) {
            // Show the chosen/captured photo, filling the circle
            Image(
                bitmap = selectedImageBitmap.asImageBitmap(),
                contentDescription = "Selected profile photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(circleSize - 12.dp)
                    .clip(CircleShape)
            )
        } else {
            // Placeholder camera icon from drawable/cam.png, tinted to match the app's blue
            Image(
                painter = painterResource(id = R.drawable.cam),
                contentDescription = "Add profile photo",
                colorFilter = ColorFilter.tint(ForgotPasswordBlue),
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

/**
 * Decodes a Bitmap from a content Uri (works for both gallery picks and
 * FileProvider-backed camera captures).
 */
private fun uriToBitmap(context: android.content.Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    } catch (_: Exception) {
        null
    }
}

/**
 * Creates a content Uri (via FileProvider) that the camera app can write
 * a full-resolution photo into. Requires a FileProvider entry in the
 * manifest — see notes below the code.
 */
@Suppress("SpellCheckingInspection")
private fun createImageUri(context: android.content.Context): Uri {
    val imagesDir = File(context.cacheDir, "images").apply { mkdirs() }
    val imageFile = File(imagesDir, "profile_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    name = "Phone Preview"
)
@Composable
fun PhotoScreenPreview() {
    NavHighTheme {
        PhotoScreen(
            onAddPhotoClick = {},
            onSkipClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    name = "Tablet Preview",
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
fun PhotoScreenTabletPreview() {
    NavHighTheme {
        PhotoScreen(
            onAddPhotoClick = {},
            onSkipClick = {}
        )
    }
}