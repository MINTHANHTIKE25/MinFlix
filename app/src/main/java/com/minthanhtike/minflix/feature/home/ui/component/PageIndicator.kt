package com.minthanhtike.minflix.feature.home.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.minthanhtike.minflix.R

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val width = animateDpAsState(targetValue = if (isSelected) 24.dp else 6.dp, label = "")
    val height = animateDpAsState(targetValue = if (isSelected) 8.dp else 6.dp, label = "")
    val color = animateColorAsState(
        targetValue = if (isSelected)
            colorResource(id = R.color.primary_color) else
            colorResource(id = R.color.gray_color), label = ""
    )
    Box(
        modifier = modifier
            .padding(2.dp)
            .width(width.value)
            .height(height.value)
            .clip(CircleShape)
            .background(color.value)
    )
}