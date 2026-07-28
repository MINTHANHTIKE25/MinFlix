package com.minthanhtike.minflix.feature.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minthanhtike.minflix.ui.theme.MinFlixTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScn(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Testing")
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "Search Screen",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = modifier.align(Alignment.Center)
            )
        }
    }

}

@Preview
@Composable
private fun PrevSearchScn() {
    MinFlixTheme {
        SearchScn(
            paddingValues = PaddingValues()
        )
    }

}