package com.minthanhtike.minflix.feature.favourite.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun FavScn(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    Box(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Text(
            text = "Favourite Screen",
            fontSize = 30.sp,
            color = Color.White,
            modifier = modifier.align(Alignment.Center)
        )
    }
}