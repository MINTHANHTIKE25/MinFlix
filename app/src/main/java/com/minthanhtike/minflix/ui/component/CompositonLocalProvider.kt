package com.minthanhtike.minflix.ui.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf


@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope =
    compositionLocalOf<SharedTransitionScope> {
        noLocalProvidedFor("SharedTransitionScope")
    }

val LocalAnimatedContentScope =
    compositionLocalOf<AnimatedContentScope> {
        noLocalProvidedFor("AnimatedContentScope")
    }

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
