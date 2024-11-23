package com.minthanhtike.minflix.feature.detail.ui

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import com.minthanhtike.minflix.ui.component.LocalAnimatedContentScope
import com.minthanhtike.minflix.ui.component.LocalSharedTransitionScope

private val POSTERIMG = "posterImg"
private val TITLETXT = "titleTxt"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScn(
    modifier: Modifier = Modifier,
    id: String, name: String,
    image: String
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current
    val constraintSet = ConstraintSet {
        val posterImgRef = createRefFor(POSTERIMG)
        val titleTxtRef = createRefFor(TITLETXT)
        constrain(ref = posterImgRef) {
            top.linkTo(parent.top, 100.dp)
            start.linkTo(parent.start, 20.dp)
        }

        constrain(ref = titleTxtRef) {
            top.linkTo(posterImgRef.top)
            bottom.linkTo(posterImgRef.bottom)
            start.linkTo(posterImgRef.end, 30.dp)
        }
    }

    val cornerSize =
        animatedContentScope?.transition?.animateDp(
            label = "corner",
            transitionSpec = {
                tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
            }
        ) { exitEnter ->
            when (exitEnter) {
                EnterExitState.PreEnter -> 16.dp
                EnterExitState.Visible -> 24.dp
                EnterExitState.PostExit -> 16.dp
            }
        }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 0, 0, 100)),
        constraintSet = constraintSet
    ) {
        if (sharedTransitionScope != null && animatedContentScope != null) {
            with(sharedTransitionScope) {
                AsyncImage(
                    model = image,
                    contentDescription = "name",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .layoutId(POSTERIMG)
                        .size(width = 120.dp, height = 180.dp)
                        .sharedElement(
                            state = sharedTransitionScope
                                .rememberSharedContentState(key = "image-$id"),
                            animatedVisibilityScope = animatedContentScope,
                            clipInOverlayDuringTransition =
                            OverlayClip(
                                RoundedCornerShape(cornerSize?.value ?: 24.dp)
                            ),
                            boundsTransform = { initialBounds, targetBounds ->
                                tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
                            },
                        )
                )
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = name,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .layoutId(TITLETXT)
                        .sharedElement(
                            state = sharedTransitionScope
                                .rememberSharedContentState(key = "text-$id"),
                            animatedVisibilityScope = animatedContentScope
                        )
                )
            }
        }
    }

}