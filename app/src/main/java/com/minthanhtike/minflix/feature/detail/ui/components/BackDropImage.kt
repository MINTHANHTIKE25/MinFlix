package com.minthanhtike.minflix.feature.detail.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.minthanhtike.minflix.common.shimmerEffect

@Composable
fun BackdropImage(
    modifier: Modifier = Modifier,
    image: String,
    contentDes: String,
    isDetailUiLoading: Boolean
) {
    SubcomposeAsyncImage(
        model = image,
        contentDescription = contentDes,
        contentScale = ContentScale.Crop,
        loading = { Box(modifier = modifier.shimmerEffect(true)) },
        modifier = modifier
            .shimmerEffect(isDetailUiLoading)
            .graphicsLayer { alpha = 1f }
            .drawWithContent {
                val colors =
                    listOf(
                        Color.Transparent,
                        Color.Black
                    )
                drawContent()
                drawRect(
                    brush = Brush.linearGradient(
                        colors = colors,
                        start = Offset(0f, size.height / 2), // Start at the middle
                        end = Offset(0f, size.height) // End at the bottom
                    ),
                    topLeft = Offset(
                        0f,
                        size.height / 2
                    ), // Rectangle starts halfway
                    size = Size(size.width, size.height / 2) // Half of the height
                )
            }
    )
}