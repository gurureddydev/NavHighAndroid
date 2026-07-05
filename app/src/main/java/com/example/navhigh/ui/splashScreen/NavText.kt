package com.example.navhigh.ui.splashScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.example.navhigh.R
import com.example.navhigh.ui.theme.GradientEnd
import com.example.navhigh.ui.theme.GradientStart
import com.example.navhigh.ui.theme.TrackColor
import com.example.navhigh.ui.theme.ProgressStart
import com.example.navhigh.ui.theme.ProgressEnd

// ---------------------- LOGO ----------------------
@Composable
fun Logo(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.nav_high_logo),
            contentDescription = "NavHighLogo",
            contentScale = ContentScale.Fit
        )
    }
}

// ---------------------- TITLE ----------------------
@Composable
fun NavHighTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nav",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "High",
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    ),
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Text(
            text = "Share Your Voice. Reach New Heights.",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

// ---------------------- LOADING ----------------------
@Composable
fun LoadingSection(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .width(170.dp)
                .height(4.dp)
        ) {

            // Track
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(TrackColor, CircleShape)
            )

            // Progress
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
            ) {

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(10.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(ProgressStart, ProgressEnd)
                            ),
                            CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(ProgressStart, ProgressEnd)
                            ),
                            CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(34.dp))

        Text(
            text = "Loading...",
            style = TextStyle(
                color = Color.White.copy(alpha = 0.68f),
                fontWeight = FontWeight.Light
            )
        )
    }
}

// ---------------------- MAIN SCREEN ----------------------
@Composable
fun NavTextScreen(progress: Float = 0.5f) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF030814))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Logo()
                Spacer(modifier = Modifier.height(20.dp))
                NavHighTitle()
            }

            LoadingSection(
                progress = progress,
                modifier = Modifier
                    .padding(bottom = 80.dp)
            )
        }
    }
}

// ---------------------- PREVIEW ----------------------
@Preview(
    showBackground = true,
    backgroundColor = 0xFF030814,
    showSystemUi = true
)
@Composable
fun NavTextScreenPreview() {
    NavTextScreen(progress = 0.6f)
}