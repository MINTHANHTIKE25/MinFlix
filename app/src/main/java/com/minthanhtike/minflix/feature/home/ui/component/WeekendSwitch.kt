package com.minthanhtike.minflix.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun WeekendSwitch(
    modifier: Modifier = Modifier,
    stateOn: Int, stateOff: Int,
    width: Dp, height: Dp,
    dayFontSize: Int,
    weekFontSize: Int,
    onClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(0)
    val sizePx =
        with(LocalDensity.current) { (width / 2).toPx() } // Minus the height to avoid the inner box going outside
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states
    Box(
        modifier = modifier
            .height(height)
            .width(width)
            .clip(RoundedCornerShape(height))
            .border(1.dp, color = colorResource(id = R.color.primary_color), CircleShape)
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(colorResource(id = R.color.primary_color))
        )
        Text(
            text = "Today",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = dayFontSize.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(
                Font(
                    googleFont = FontProvider.fontNameInter,
                    fontProvider = FontProvider.provider
                )
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterStart)
                .clickable {
                    scope.launch {
                        swipeableState.animateTo(stateOn)
                        onClick(swipeableState.currentValue)
                    }
                }
        )

        Text(
            text = "This Week",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = weekFontSize.sp,
            fontFamily = FontFamily(
                Font(
                    googleFont = FontProvider.fontNameInter,
                    fontProvider = FontProvider.provider
                )
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterEnd)
                .clickable {
                    scope.launch {
                        swipeableState.animateTo(stateOff)
                        onClick(swipeableState.currentValue)
                    }
                }
        )
    }
}

@Preview
@Composable
private fun WeekendSwitchPrev() {
    WeekendSwitch(
        width = 200.dp, height = 30.dp, stateOn = 0, stateOff = 1,
        dayFontSize = 12,
        weekFontSize = 12
    ) {}
}