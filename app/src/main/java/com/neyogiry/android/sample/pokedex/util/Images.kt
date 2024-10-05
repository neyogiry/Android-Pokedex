package com.neyogiry.android.sample.pokedex.util

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.memory.MemoryCache

@Composable
fun Image(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    averageColor: ((Color) -> Unit)? = null,
){
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)
        .newBuilder()
        .allowHardware(false)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .build()

    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        modifier = modifier,
        onSuccess = {
        averageColor?.let { averageColor ->
            val bitmap = it.result.drawable.toBitmap()
            bitmap.averageColor { color ->
                averageColor(color)
            }
        }
    })
}

fun Bitmap.averageColor(color: (Color) -> Unit) {
    val builder = Palette.Builder(this)
    builder.generate {
        color(
            Color(it?.getDominantColor(it.vibrantSwatch?.population ?: Color.White.toArgb())  ?: Color.White.toArgb())
        )
    }
}
