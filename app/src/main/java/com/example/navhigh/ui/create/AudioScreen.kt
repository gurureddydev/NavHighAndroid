
package com.example.navhigh.ui.create

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.navhigh.R
import kotlinx.coroutines.delay
import java.io.File
import kotlin.random.Random

@Composable
fun AudioScreen() {
    var recordedFile by remember { mutableStateOf<File?>(null) }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text("2. Add Voice", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))

        if (recordedFile == null) {
            AddVoiceOnlyComponent(onSave = { file -> recordedFile = file })
        } else {
            RecordedAudioPlayer(
                file = recordedFile!!,
                onDelete = {
                    recordedFile?.delete()
                    recordedFile = null
                }
            )
        }
    }
}

@Composable
fun RecordedAudioPlayer(file: File, onDelete: () -> Unit) {
    var isPlaying by remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer() }
    val context = LocalContext.current
    DisposableEffect(Unit) { onDispose { mediaPlayer.release() } }

    Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF161A22)), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (isPlaying) {
                    mediaPlayer.pause()
                } else {
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(file.absolutePath)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                    mediaPlayer.setOnCompletionListener { isPlaying = false }
                }
                isPlaying = !isPlaying
            }) {
                Icon(if (isPlaying) Icons.Outlined.Pause else Icons.Default.PlayArrow, "Play/Pause", tint = Color.White)
            }
            Text("Voice Recorded", color = Color.White, modifier = Modifier.weight(1f))

            // Save Button
            IconButton(onClick = { Toast.makeText(context, "Saved to Drafts", Toast.LENGTH_SHORT).show() }) {
                Icon(Icons.Default.Check, "Save", tint = Color.Green)
            }

            // Delete Button
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Delete", tint = Color.Red)
            }
        }
    }
}

@Composable
fun AddVoiceOnlyComponent(onSave: (File) -> Unit) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val visualizer = remember { AudioVisualizerHelper(context) }
    var isRecording by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var timeElapsed by remember { mutableLongStateOf(0L) }
    val waveSamples = remember { mutableStateListOf<Float>() }
    val markers = remember { mutableStateListOf<Long>() }
    var scrollOffset by remember { mutableFloatStateOf(0f) }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) { isRecording = true; isPaused = false }
    }

    val barWidth = 6f
    val gap = 4f
    val step = barWidth + gap
    val stroke = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))

    LaunchedEffect(isRecording, isPaused) {
        if (isRecording && !isPaused) {
            if (!isPreview) visualizer.startRecording()
            while (isRecording) {
                delay(100)
                if (!isPaused) {
                    val sample = if (isPreview) Random.nextInt(10, 50).toFloat() else visualizer.getAmplitude()
                    waveSamples.add(sample)
                    timeElapsed += 100
                    scrollOffset += step
                }
            }
        } else { visualizer.stopRecording() }
    }

    Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF161A22)), modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(2.dp).drawBehind { drawRoundRect(color = Color.Gray, style = stroke, cornerRadius = CornerRadius(20.dp.toPx())) }.padding(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(formatTime(timeElapsed), color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Light)

                Canvas(modifier = Modifier.fillMaxWidth().height(45.dp)) {
                    val centerY = size.height / 2
                    waveSamples.forEachIndexed { index, height ->
                        val x = (index * step) - scrollOffset + (size.width / 2)
                        if (x in 0f..size.width) drawRoundRect(Color.White, Offset(x, centerY - height / 2), Size(barWidth, height), CornerRadius(2f))
                    }
                    markers.forEach { mTime ->
                        val mX = (mTime / 100 * step) - scrollOffset + (size.width / 2)
                        if (mX in 0f..size.width) drawLine(Color.Red, Offset(mX, 0f), Offset(mX, size.height), strokeWidth = 4f)
                    }
                    drawLine(Color.White, Offset(size.width / 2, 0f), Offset(size.width / 2, size.height), strokeWidth = 2f)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    IconButton(onClick = { if (isRecording) markers.add(timeElapsed) }) {
                        Icon(painterResource(id = R.drawable.check), "Marker", tint = Color.White, modifier = Modifier.size(24.dp))
                    }

                    Box(modifier = Modifier.size(65.dp).border(3.dp, Brush.verticalGradient(listOf(Color.Red, Color.Magenta)), CircleShape).clickable {
                        if (!isRecording) {
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                                isRecording = true
                            } else { permissionLauncher.launch(Manifest.permission.RECORD_AUDIO) }
                        } else { isPaused = !isPaused }
                    }, contentAlignment = Alignment.Center) {
                        Icon(imageVector = if (isRecording && !isPaused) Icons.Outlined.Pause else Icons.Default.Mic, "Toggle", tint = Color.White, modifier = Modifier.size(30.dp))
                    }

                    IconButton(onClick = {
                        isRecording = false
                        isPaused = false
                        visualizer.stopRecording()
                        visualizer.recordedFile?.let { onSave(it) }
                    }) {
                        Icon(Icons.Default.Check, "Finish", tint = Color.White, modifier = Modifier.size(30.dp))
                    }
                }
            }
        }
    }
}

class AudioVisualizerHelper(private val context: Context) {
    private var recorder: MediaRecorder? = null
    var recordedFile: File? = null

    fun startRecording() {
        recordedFile = File(context.filesDir, "audio_${System.currentTimeMillis()}.3gp")
        recorder = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MediaRecorder(context) else MediaRecorder()).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordedFile?.absolutePath)
            try { prepare(); start() } catch (_: Exception) {}
        }
    }

    fun getAmplitude(): Float = (recorder?.maxAmplitude?.toFloat() ?: 0f) / 32767f * 60f

    fun stopRecording() {
        try { recorder?.apply { stop(); release() } } catch (_: Exception) {}
        recorder = null
    }
}

fun formatTime(millis: Long): String = "%02d:%02d".format((millis / 60000) % 60, (millis / 1000) % 60)

@Preview(showBackground = true)
@Composable
fun PreviewAudioScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF0B0D13)) {
        AudioScreen()
    }
}

