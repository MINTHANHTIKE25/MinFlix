package com.minthanhtike.minflix.feature.home.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.minthanhtike.minflix.common.FontProvider

/**
 * Header for the home's movies lazy row
 */
@Composable
fun ItemHeader(
    modifier: Modifier = Modifier, trendMovieConstraintSet: ConstraintSet,
    headerText:String,
    headerTextId:String,
    daySwitchId:String,
    onTrendDateSelect:(String) -> Unit,
) {
    ConstraintLayout(
        constraintSet = trendMovieConstraintSet,
        modifier = modifier.padding(start = 20.dp, end = 10.dp),
    ) {
        Text(
            modifier = Modifier.layoutId(headerTextId),
            text = headerText,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(
                Font(
                    googleFont = FontProvider.fontNameInter,
                    fontProvider = FontProvider.provider
                )
            )
        )
        WeekendSwitch(
            width = 160.dp,
            height = 30.dp,
            stateOn = 0,
            stateOff = 1,
            weekFontSize = 12,
            dayFontSize = 12,
            modifier = Modifier.layoutId(daySwitchId),
            onClick = { currentDate ->
                if (currentDate == 0) {
                    onTrendDateSelect("day")
                } else {
                    onTrendDateSelect("week")
                }
            }
        )
    }
}