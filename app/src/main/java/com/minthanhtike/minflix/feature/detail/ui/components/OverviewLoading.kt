package com.minthanhtike.minflix.feature.detail.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.minthanhtike.minflix.common.ShimmerEffect

@Composable
fun OverviewLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        ShimmerEffect(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .width(60.dp)
                .height(15.dp)
        )
        repeat(3) {
            ShimmerEffect(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .height(16.dp)
            )
        }
    }
}


@Composable
fun InfosLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        repeat(3) { times ->
            ShimmerEffect(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .width(
                        width = when (times) {
                            0 -> 120.dp
                            2 -> 100.dp
                            1 -> 80.dp
                            else -> 80.dp
                        }
                    )
                    .height(10.dp)
            )
        }
    }
}