package com.minthanhtike.minflix.feature.home.ui.component

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.minthanhtike.minflix.common.ShimmerEffect
import com.minthanhtike.minflix.common.shimmerEffect

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PosterCard(
    id: String,
    modifier: Modifier = Modifier,
    posterImg: String,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    onClick: () -> Unit,
    name: String,
    textWidth:Int
) {
    val cornerSize =
        animatedContentScope?.transition?.animateDp(
            label = "corner",
            transitionSpec = {
                tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
            }
        ) { exitEnter ->
            when (exitEnter) {
                EnterExitState.PreEnter -> 24.dp
                EnterExitState.Visible -> 16.dp
                EnterExitState.PostExit -> 24.dp
            }
        }

    var isLoading by remember { mutableStateOf(false) }

    if (sharedTransitionScope != null && animatedContentScope != null) {
        Column() {
            with(sharedTransitionScope) {
                AsyncImage(
                    model = posterImg,
                    contentDescription = "PosterPath",
                    modifier = modifier
                        .sharedElement(
                            state = sharedTransitionScope.rememberSharedContentState(key = "image-$id"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = { initialBounds, targetBounds ->
                                tween(durationMillis = 2000, easing = FastOutSlowInEasing)
                            },
                            clipInOverlayDuringTransition =
                            OverlayClip(
                                RoundedCornerShape(cornerSize?.value ?: 16.dp)
                            ),
                        )
                        .clickable { onClick() }
                        .shimmerEffect(isLoading = isLoading),
                    contentScale = ContentScale.FillBounds,
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Loading -> {
                                isLoading = true
                            }

                            is AsyncImagePainter.State.Error -> {}
                            is AsyncImagePainter.State.Empty -> {}
                            is AsyncImagePainter.State.Success -> {
                                isLoading = false
                            }
                        }

                    }
                )
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(textWidth.dp)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .sharedElement(
                            state = sharedTransitionScope.rememberSharedContentState(key = "text-$id"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = { initialBounds, targetBounds ->
                                tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
                            },
                        ),
                    fontSize = 14.sp,
                    color = Color.White,
                )
            }
        }

    }
}