package com.minthanhtike.minflix.feature.detail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.common.shimmerEffect

@Composable
fun CasterCrewCard(
    modifier: Modifier = Modifier,
    img: String,
    contentDes: String,
    name: String,
    department: String
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .wrapContentHeight(),
    ) {
        SubcomposeAsyncImage(
            model = img,
            contentDescription = contentDes,
            contentScale = ContentScale.FillBounds,
            modifier = modifier,
            loading = {
                Box(modifier = Modifier.shimmerEffect(true))
            },
            error = {
                Box(
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .fillMaxSize()
                )
            }
        )

        Text(
            text = name,
            modifier = Modifier.padding(top = 5.dp),
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(
                Font(
                    googleFont = FontProvider.fontNameInter,
                    fontProvider = FontProvider.provider
                )
            )
        )

        Text(
            text = department,
            modifier = Modifier.padding(top = 5.dp),
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(
                Font(
                    googleFont = FontProvider.fontNameInter,
                    fontProvider = FontProvider.provider
                )
            )
        )

    }
}