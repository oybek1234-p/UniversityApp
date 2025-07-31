package com.example.universityapp.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerScreen() {
    val totalSeconds = 60

    var animationStarted by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        targetValue = if (animationStarted) 0.0f else 1.0f, animationSpec = tween(
            durationMillis = totalSeconds * 1000, easing = LinearEasing
        ), label = "TimerProgress"
    )
    LaunchedEffect(key1 = true) {
        animationStarted = true
    }
    val remainingSeconds = (progress * totalSeconds).toInt()

    val progressColor = if(remainingSeconds in 1..10) {
        Color.Red
    } else {
        Color(0xFF0085FF)
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Timer(
            progress = progress, progressColor = progressColor, size = 280.dp
        )
        TimerText(seconds = remainingSeconds)
    }
}

@Composable
fun Timer(
    progress: Float, progressColor: Color, size: Dp, strokeWidth: Dp = 12.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        drawArc(
            color = Color.Gray.copy(alpha = 0.3f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx())
        )
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = progress * 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun TimerText(seconds: Int) {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    Text(
        text = String.format("%02d:%02d", minutes, remainingSeconds),
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 72.sp,
        fontWeight = FontWeight.Light
    )
}