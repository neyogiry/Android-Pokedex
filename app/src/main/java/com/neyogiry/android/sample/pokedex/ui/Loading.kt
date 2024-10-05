package com.neyogiry.android.sample.pokedex.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(rotationZ = rotation),
        contentAlignment = Alignment.Center,
    ) {
        Pokeball()
    }
}

@Composable
private fun Pokeball() {
    Box {
        Canvas(
            modifier = Modifier
                .size(203.dp)
                .border(1.dp, Color.Black, CircleShape)
        ) {
            val center = Offset(x = size.width/ 2, y = size.height / 2)
            val radius = 100.dp.toPx()

            drawArc(
                color = Color.Red,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(x = center.x - radius, y = center.y - radius),
                size = Size(width = 2 * radius, height = 2 * radius)
            )

            drawArc(
                color = Color.White,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(x = center.x - radius, y = center.y - radius),
                size = Size(width = 2 * radius, height = 2 * radius)
            )

            drawRect(
                color = Color.Black,
                topLeft = Offset(x = size.width / 2 - 100.dp.toPx(), y = size.height / 2 - 9.dp.toPx()),
                size = Size(width = 200.dp.toPx(), height = 18.dp.toPx())
            )

            drawCircle(
                color = Color.Black,
                radius = 30.dp.toPx(),
                center = center
            )

            drawCircle(
                color = Color.White,
                radius = 15.dp.toPx(),
                center = center
            )

        }
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}