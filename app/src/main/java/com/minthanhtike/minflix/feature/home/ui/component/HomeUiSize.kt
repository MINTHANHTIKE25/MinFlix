package com.minthanhtike.minflix.feature.home.ui.component

import android.content.res.Configuration
import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.minthanhtike.minflix.common.currentWindowSizeInfo

data class HomeUiSize(
    val pagerHeight: Float,
    val pagerItemHeight: Float,
    val pagerContentPadding: PagerContentPadding,
    val posterCardSize: PosterCardSize
)

data class PagerContentPadding(
    val firstItemPaddingEnd: Int,
    val firstItemPaddingStart: Int,
    val lastItemPaddingStart: Int,
    val lastItemPaddingEnd: Int,
    val horizontalPadding: Int
)

data class PosterCardSize(
    val width: Int, val height: Int
)

@Composable
fun getHomeUiSize(): HomeUiSize {
    val config = LocalConfiguration.current

    val scnWidth = config.screenWidthDp.toDouble()
    Log.wtf("scnWidth", "$scnWidth")
    val paddingMiddle = if (scnWidth !in 840.0..1000.0) scnWidth * 0.14 else scnWidth * 0.06
    Log.wtf("paddingBetween", "$paddingMiddle")
    val firstLastPadding = if (scnWidth !in 840.0..1000.0) scnWidth * 0.2 else scnWidth * 0.1
    Log.wtf("paddingStartEnd", "$firstLastPadding")


    Log.wtf("orientaion","${config.orientation}")
    return when (currentWindowSizeInfo().widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            HomeUiSize(
                pagerHeight = 0.4f,
                pagerItemHeight = 0.8f,
                pagerContentPadding = PagerContentPadding(
                    firstItemPaddingEnd = 32,
                    firstItemPaddingStart = 20,
                    lastItemPaddingStart = 32,
                    lastItemPaddingEnd = 20,
                    horizontalPadding = 32
                ),
                posterCardSize = PosterCardSize(
                    width = 140,
                    height = 210,
                )
            )
        }

        WindowWidthSizeClass.Medium -> {
            HomeUiSize(
                pagerItemHeight = 0.8f,
                pagerHeight = 0.5f,
                pagerContentPadding = PagerContentPadding(
                    firstItemPaddingEnd = 180,
                    firstItemPaddingStart = 20,
                    lastItemPaddingStart = 180,
                    lastItemPaddingEnd = 20,
                    horizontalPadding = 62
                ),
                posterCardSize = PosterCardSize(
                    width = 180,
                    height = 250,
                )
            )
        }
        WindowWidthSizeClass.Expanded -> {
            HomeUiSize(
                pagerHeight = when(config.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> 0.4f
                    Configuration.ORIENTATION_LANDSCAPE -> 0.7f
                    else -> 0.6f
                },
                pagerItemHeight = 0.9f,
                pagerContentPadding = PagerContentPadding(
                    firstItemPaddingEnd = firstLastPadding.toInt(),
                    firstItemPaddingStart = 20,
                    lastItemPaddingStart = firstLastPadding.toInt(),
                    lastItemPaddingEnd = 20,
                    horizontalPadding = paddingMiddle.toInt()
                ),
                posterCardSize = PosterCardSize(
                    width = 220,
                    height = 300,
                )
            )
        }

        else -> HomeUiSize(
            pagerHeight = 0.6f,
            pagerItemHeight = 0.8f,
            pagerContentPadding = PagerContentPadding(
                firstItemPaddingEnd = 152,
                firstItemPaddingStart = 20,
                lastItemPaddingStart = 152,
                lastItemPaddingEnd = 20,
                horizontalPadding = 152
            ),
            posterCardSize = PosterCardSize(
                width = 260,
                height = 310,
            )
        )
    }
}

