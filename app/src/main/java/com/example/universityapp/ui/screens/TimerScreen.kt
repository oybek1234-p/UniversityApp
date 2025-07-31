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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

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

    val progressColor = if(remainingSeconds in 0..10) {
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
    progress: Float,
    progressColor: Color,
    size: Dp,
    strokeWidth: Dp = 12.dp,
    fadeLengthDegrees: Float = 30f
) {
    Canvas(modifier = Modifier.size(size)) {
        drawArc(
            color = Color.Gray.copy(alpha = 0.3f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx())
        )

        if (progress <= 0f) return@Canvas

        val totalSweepAngle = progress * 360f

        val fadeAngle = fadeLengthDegrees.coerceAtMost(totalSweepAngle)
        val solidAngle = totalSweepAngle - fadeAngle

        if (totalSweepAngle > 0) {
            val pathRadius = (size.toPx() - strokeWidth.toPx()) / 2f
            val angleInRad = Math.toRadians(-90.0)

            val circleCenterX = center.x + pathRadius * cos(angleInRad).toFloat()
            val circleCenterY = center.y + pathRadius * sin(angleInRad).toFloat()

            drawCircle(
                color = progressColor,
                radius = strokeWidth.toPx() / 2f,
                center = Offset(circleCenterX+5, circleCenterY-18)
            )
        }

        if (solidAngle > 0) {
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = solidAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
            )
        }

        if (fadeAngle > 0) {
            val fadeStartAngle = -90f + solidAngle
            val steps = fadeAngle.toInt()
            if (steps <= 0) return@Canvas

            for (i in 0 until steps) {
                val currentProgressInFade = i.toFloat() / steps
                val currentColor = lerp(progressColor, Color.Transparent, currentProgressInFade)

                drawArc(
                    color = currentColor,
                    startAngle = fadeStartAngle + i,
                    sweepAngle = 1.01f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
                )
            }
        }
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