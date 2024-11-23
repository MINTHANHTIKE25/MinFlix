package com.minthanhtike.minflix.ui.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf


@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope =
    compositionLocalOf<SharedTransitionScope?> {
        null
    }

val LocalAnimatedContentScope =
    compositionLocalOf<AnimatedContentScope?> {
        null
    }
