package com.minthanhtike.minflix.feature.detail.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.common.TabItems
import com.minthanhtike.minflix.feature.detail.ui.HomeDetailAction
import kotlinx.coroutines.launch

@Composable
fun CustomTabRow(
    modifier: Modifier = Modifier, tabList: List<TabItems>, onTabSelect: (HomeDetailAction) -> Unit
) {
    val scope = rememberCoroutineScope()
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val localDensity = LocalDensity.current

    val animatedOffset = remember { Animatable(0f) }
    val animatedWidth = remember { Animatable(0f) }

    // State to track whether user is scrolling
    val lazyListState = rememberLazyListState()

    val isSelectedTabVisible by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.any { item ->
                item.index == selectedTabIndex && item.offset >= 0
            }
        }
    }



    LaunchedEffect(selectedTabIndex) {
        onTabSelect(tabList[selectedTabIndex].action)
        // Scroll to the selected tab and update the divider
        lazyListState.animateScrollToItem(selectedTabIndex)
        scope.launch {
            val visibleItem =
                lazyListState.layoutInfo.visibleItemsInfo.find { it.index == selectedTabIndex }
            if (visibleItem != null) {
                animatedOffset.animateTo(visibleItem.offset.toFloat())
                animatedWidth.animateTo(visibleItem.size.toFloat())
            }
        }
    }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), state = lazyListState
        ) {
            itemsIndexed(tabList) { index: Int, tab: TabItems ->
                Text(text = tab.label, fontSize = 14.sp, style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ), fontFamily = FontFamily(
                        Font(
                            googleFont = FontProvider.fontNameInter,
                            fontProvider = FontProvider.provider
                        )
                    ), fontWeight = FontWeight.SemiBold, color = Color.White
                ), textAlign = TextAlign.Center, modifier = Modifier
                    .clickable(
                        indication = ripple(bounded = true, color = Color.White),
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        selectedTabIndex = index
                    }
                    .onGloballyPositioned {
                        if (index == selectedTabIndex) {
                            scope.launch {
                                animatedOffset.animateTo(it.positionInParent().x)
                                animatedWidth.animateTo(it.size.width.toFloat())
                            }
                        }
                    }
                    .padding(horizontal = 10.dp))
            }
        }

        //region Animated Horizontal Divider
        if (isSelectedTabVisible) {
            Box(modifier = Modifier
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
}

@Composable
fun MyTabRow(
    modifier: Modifier = Modifier, tabList: List<TabItems>, onTabSelect: (HomeDetailAction) -> Unit
) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    ScrollableTabRow(selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        edgePadding = 0.dp,
        contentColor = Color.LightGray,
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .fillMaxWidth(),
                height = 4.dp,
                color = colorResource(id = R.color.primary_color)
            )
        }) {
        tabList.onEachIndexed { index, tabItems ->
            Tab(
                selected = true,
                onClick = {
                    selectedTabIndex = index
                    onTabSelect(tabList[selectedTabIndex].action)
                }
            ) {
                Text(
                    text = tabItems.label,
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontFamily = FontFamily(
                            Font(
                                googleFont = FontProvider.fontNameInter,
                                fontProvider = FontProvider.provider
                            )
                        ), fontWeight = FontWeight.SemiBold, color = Color.White
                    ),
                )
            }
        }
    }
}