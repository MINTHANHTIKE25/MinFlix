package com.minthanhtike.minflix.feature.detail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeDetailSupportingPane(modifier: Modifier = Modifier) {
    val list = buildList<Int> {
        repeat(10) {
            add(R.drawable.ic_launcher_background)
        }
    }
    LazyColumn(
        modifier = modifier,
    ) {
        stickyHeader(key = 1) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Black)
                    .padding(bottom = 10.dp),
            ) {
                Text(
                    text = "More like this",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(
                        Font(
                            googleFont = FontProvider.fontNameInter,
                            fontProvider = FontProvider.provider
                        )
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    thickness = 2.dp,
                    color = Color.DarkGray
                )
            }

        }
        items(list) {
            Image(
                painter = painterResource(id = it),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrevHomeDetailSupportPane() {
    HomeDetailSupportingPane(
        modifier = Modifier.fillMaxSize()
    )
}