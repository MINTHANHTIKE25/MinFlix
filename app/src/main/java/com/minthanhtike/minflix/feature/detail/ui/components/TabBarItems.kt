package com.minthanhtike.minflix.feature.detail.ui.components

import android.os.Parcelable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.common.TabItems
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class TabWidth(
    val tabList: MutableList<Float> = mutableListOf()
):Parcelable

@Composable
fun CustomTabRow(
    modifier: Modifier = Modifier,
    tabList: List<TabItems>,
    onTabSelect: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val localDensity = LocalDensity.current

    val animatedOffset = remember { Animatable(0f) }
    val animatedWidth = remember { Animatable(0f) }

    // Store calculated widths for each tab
    val tabTxtWidths = rememberSaveable { mutableStateOf(TabWidth()) }

    LaunchedEffect(selectedTabIndex) {
        onTabSelect(tabList[selectedTabIndex].label)
        // Animate the divider's position when the selected tab changes
        scope.launch {
            animatedOffset.animateTo(tabTxtWidths.value.tabList.take(selectedTabIndex).sum())
            animatedWidth.animateTo(tabTxtWidths.value.tabList[selectedTabIndex])
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
        ) {
            tabList.forEachIndexed { index, tab ->
                Text(
                    text = tab.label,
                    fontSize = 14.sp,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        fontFamily = FontFamily(
                            Font(
                                googleFont = FontProvider.fontNameInter,
                                fontProvider = FontProvider.provider
                            )
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(
                            indication = ripple(bounded = true, color = Color.White),
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            selectedTabIndex = index
                        }
                        .onSizeChanged {
                            if (index >= tabTxtWidths.value.tabList.size) {
                                tabTxtWidths.value.tabList.add((it.width).toFloat()) // Collect tab widths
                            }
                        }
                        .padding(horizontal = 10.dp)
                )
            }
        }

        //region Animated Horizontal Divider
        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffset.value.toInt(), 0) }
                .width(with(localDensity) { animatedWidth.value.toDp() })
                .height(4.dp)
                .background(
                    color = colorResource(id = R.color.primary_color),
                    shape = RoundedCornerShape(2.dp)
                )
        )
    }
}