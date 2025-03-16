package com.minthanhtike.minflix.feature.detail.ui.components

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.minthanhtike.minflix.common.currentWindowSizeInfo

data class HomeDetailUiSize(
    val backdropImgHeight:Int
)

@Composable
fun getHomeDetailUiSize():HomeDetailUiSize{
    val windowSize = currentWindowSizeInfo()
    val config = LocalConfiguration.current
    val height = config.screenHeightDp
    return when(windowSize.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeDetailUiSize(
            backdropImgHeight = (height * 0.35f).toInt()
        )
        WindowWidthSizeClass.Medium -> HomeDetailUiSize(
            backdropImgHeight = (height * 0.45f).toInt()
        )
        WindowWidthSizeClass.Expanded -> HomeDetailUiSize(
            backdropImgHeight = (height * 0.45f).toInt()
        )

        else -> HomeDetailUiSize(
            backdropImgHeight = (height * 0.45f).toInt()
        )
    }
}