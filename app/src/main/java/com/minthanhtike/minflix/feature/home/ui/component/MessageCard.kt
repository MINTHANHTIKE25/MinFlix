package com.minthanhtike.minflix.feature.home.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageCard(
    modifier: Modifier = Modifier,
    title: String,
    buttonText: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier =
        modifier.padding(
            vertical = 21.dp,
            horizontal = 16.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 16.41.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = buttonText,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
            lineHeight = 14.06.sp,
            color = Color.Red,
            modifier = Modifier.clickable {
                onClick()
            }
        )
    }
}

@Preview
@Composable
fun EmptyCardPreview() {
    MessageCard(
        title = "No favorites yet",
        buttonText = "Search"
    )
}