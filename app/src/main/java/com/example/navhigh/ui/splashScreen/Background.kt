package com.example.navhigh.ui.splashScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.ui.Alignment
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

// =============================================================================
// COLOR PALETTE - Exact match for "Screen 2"
// =============================================================================
private val ColorBackground = Color(0xFF020613)
private val ColorDeepNavy = Color(0xFF07112E)
private val ColorElectricBlue = Color(0xFF2B7CFF)
private val ColorGlowBlue = Color(0xFF2486FF)
private val ColorCoreWhite = Color(0xFFE9F5FF)
private val ColorTerrainDot = Color(0xFF4BA8FF)

// =============================================================================
// TUNING CONSTANTS - High Fidelity
// =============================================================================
private const val TERRAIN_ROWS = 65
private const val TERRAIN_COLS = 90
private const val BAR_COUNT_PER_SIDE = 12
private const val STAR_COUNT = 160


@Composable
fun SplashBackground(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "splashBackgroundTransition")

    val breath by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "barBreath"
    )

    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 40000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "terrainWavePhase"
    )

    val flarePulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flarePulse"
    )

    val leftBars = remember { generateBarSpecs(seed = 101L) }
    val rightBars = remember { generateBarSpecs(seed = 202L) }
    val stars = remember { generateStarSpecs(seed = 303L) }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // 1. Base vertical gradient
            drawRect(
                brush = Brush.verticalGradient(
                    0.0f to ColorBackground,
                    0.4f to ColorDeepNavy,
                    0.6f to ColorDeepNavy,
                    1.0f to ColorBackground
                )
            )

            // 2. Stars/Particles
            stars.forEach { star ->
                val alpha = (star.alpha * (0.8f + 0.2f * sin(wavePhase * star.speed + star.phase))).coerceIn(0f, 1f)
                drawCircle(
                    color = ColorCoreWhite.copy(alpha = alpha),
                    radius = star.r.dp.toPx(),
                    center = Offset(star.x * size.width, star.y * size.height)
                )
            }

            // 3. Central Bloom Behind Everything
            val centerY = size.height * 0.495f
            val centerPoint = Offset(size.width * 0.5f, centerY)
            drawCircle(
                brush = Brush.radialGradient(
                    0.0f to ColorElectricBlue.copy(alpha = 0.2f * flarePulse),
                    1.0f to Color.Transparent,
                    center = centerPoint,
                    radius = 160.dp.toPx()
                ),
                radius = 160.dp.toPx(),
                center = centerPoint
            )

            // 4. Equalizer Bars (Positioned above the horizon)
            drawEqualizerGroup(leftBars, isLeft = true, breath = breath)
            drawEqualizerGroup(rightBars, isLeft = false, breath = breath)

            // 5. Horizon Flare (Sharp horizontal line)
            drawSharpHorizonFlare(flarePulse)

            // 6. Digital Terrain Grid
            drawDigitalTerrainDots(wavePhase)

            // 7. Top/Bottom Vignette
            drawRect(
                brush = Brush.verticalGradient(
                    0.0f to Color.Black.copy(alpha = 0.8f),
                    0.2f to Color.Transparent,
                    0.8f to Color.Transparent,
                    1.0f to Color.Black.copy(alpha = 0.8f)
                )
            )
        }
    }
}

private fun DrawScope.drawSharpHorizonFlare(pulse: Float) {
    val centerY = size.height * 0.495f
    val centerPoint = Offset(size.width * 0.5f, centerY)

    // Wide horizontal glow
    scale(scaleX = 4.0f, scaleY = 0.008f, pivot = centerPoint) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(ColorGlowBlue.copy(alpha = 0.4f * pulse), Color.Transparent),
                center = centerPoint,
                radius = size.width * 0.4f
            ),
            radius = size.width * 0.4f,
            center = centerPoint
        )
    }

    // Razor core line
    drawLine(
        brush = Brush.horizontalGradient(
            0.0f to Color.Transparent,
            0.48f to ColorGlowBlue.copy(alpha = 0.6f * pulse),
            0.5f to ColorCoreWhite.copy(alpha = 1.0f * pulse),
            0.52f to ColorGlowBlue.copy(alpha = 0.6f * pulse),
            1.0f to Color.Transparent
        ),
        start = Offset(0f, centerY),
        end = Offset(size.width, centerY),
        strokeWidth = 1.2.dp.toPx()
    )

    // Hot spot
    drawCircle(
        color = ColorCoreWhite.copy(alpha = 0.9f * pulse),
        radius = 1.dp.toPx(),
        center = centerPoint
    )
}

private fun DrawScope.drawEqualizerGroup(bars: List<BarSpec>, isLeft: Boolean, breath: Float) {
    val innerMargin = size.width * 0.15f
    val outerMargin = size.width * 0.05f
    val startX = if (isLeft) outerMargin else size.width * 0.58f
    val endX = if (isLeft) size.width * 0.42f else size.width - outerMargin
    
    val centerY = size.height * 0.40f
    val baseMaxHeight = size.height * 0.15f

    bars.forEach { bar ->
        val x = lerp(startX, endX, bar.x)
        val h = baseMaxHeight * bar.h * (0.8f + 0.2f * sin(breath * PI.toFloat() * 2 + bar.x * 15f))
        val alpha = (bar.alpha * (0.6f + 0.4f * breath)).coerceIn(0f, 1f)
        
        val top = centerY - h / 2f
        val bottom = centerY + h / 2f
        val stroke = bar.w.dp.toPx()

        drawLine(
            color = ColorElectricBlue.copy(alpha = alpha * 0.3f),
            start = Offset(x, top), end = Offset(x, bottom),
            strokeWidth = stroke * 4f, cap = StrokeCap.Round
        )
        drawLine(
            color = ColorGlowBlue.copy(alpha = alpha),
            start = Offset(x, top), end = Offset(x, bottom),
            strokeWidth = stroke, cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawDigitalTerrainDots(phase: Float) {
    val startY = size.height * 0.62f
    val endY = size.height * 1.15f
    val w = size.width

    for (row in 0 until TERRAIN_ROWS) {
        val t = row / (TERRAIN_ROWS - 1).toFloat()
        val depth = t.pow(3.5f) // Aggressive perspective
        val rowBaseY = lerp(startY, endY, depth)
        
        val amp = lerp(size.height * 0.001f, size.height * 0.025f, t)
        val rowW = w * lerp(0.5f, 2.5f, t)
        val startX = (w - rowW) / 2f

        for (col in 0 until TERRAIN_COLS) {
            val xFrac = col / (TERRAIN_COLS - 1).toFloat()
            val x = startX + xFrac * rowW
            
            if (x < -40f || x > w + 40f) continue

            val wave = sin(xFrac * 12f + phase + t * 8f) * 0.5f + 
                       sin(xFrac * 6f - phase * 0.3f) * 0.5f
            val y = rowBaseY + wave * amp
            
            val alpha = (t * 0.7f + 0.05f).coerceIn(0f, 0.85f)
            val dotSize = lerp(0.4f, 2.0f, t).dp.toPx()

            drawCircle(
                color = ColorTerrainDot.copy(alpha = alpha),
                radius = dotSize,
                center = Offset(x, y)
            )
        }
    }
}

// =============================================================================
// SPECS GENERATORS
// =============================================================================

private data class BarSpec(val x: Float, val h: Float, val alpha: Float, val w: Float)
private data class StarSpec(val x: Float, val y: Float, val r: Float, val alpha: Float, val speed: Float, val phase: Float)

private fun generateBarSpecs(seed: Long): List<BarSpec> {
    val random = Random(seed)
    return List(BAR_COUNT_PER_SIDE) { i ->
        BarSpec(
            x = i / (BAR_COUNT_PER_SIDE - 1).toFloat(),
            h = 0.2f + random.nextFloat() * 0.8f,
            alpha = 0.3f + random.nextFloat() * 0.7f,
            w = 0.6f + random.nextFloat() * 1.2f
        )
    }
}

private fun generateStarSpecs(seed: Long): List<StarSpec> {
    val random = Random(seed)
    return List(STAR_COUNT) {
        StarSpec(
            x = random.nextFloat(),
            y = random.nextFloat() * 0.9f,
            r = 0.2f + random.nextFloat() * 1.0f,
            alpha = 0.1f + random.nextFloat() * 0.5f,
            speed = 0.2f + random.nextFloat() * 1.0f,
            phase = random.nextFloat() * 2f * PI.toFloat()
        )
    }
}

@Composable
fun SplashBackgroundComposable(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SplashBackground(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF030814,
    showSystemUi = true
)
@Composable
fun SplashBackgroundComposablePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF030814)),
        contentAlignment = Alignment.Center
    ) {
        SplashBackgroundComposable()
    }
}