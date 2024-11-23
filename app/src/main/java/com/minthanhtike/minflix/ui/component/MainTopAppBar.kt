package com.minthanhtike.minflix.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        scrollBehavior = topAppBarScrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = Color(
                0,0,0,133
            ),
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = modifier
                        .width(IntrinsicSize.Max)
                        .align(Alignment.Center)
                ) {
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = Color.White,
                    )
                    Text(
                        text = "Min\nFlix",
                        color = Color.White,
                        fontSize = 22.sp,
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                lineHeight = 1.em,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                letterSpacing = 0.12.em
                            )
                        ),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(
                            Font(
                                googleFont = FontProvider.fontNameInter,
                                fontProvider = FontProvider.provider
                            )
                        ),
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = Color.White,
                    )
                }
            }

        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxHeight()
                    .wrapContentWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "notification",
                    tint = Color(0xFFFDFDFD),
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF639EA8))
                        .padding(10.dp)
                        .size(18.dp)
                        .clickable { /*ToDo to go to acc setting */ }
                        .align(Alignment.Center)
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .fillMaxHeight()
                    .wrapContentWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.notification_ic),
                    contentDescription = "notification",
                    tint = colorResource(id = R.color.primary_color),
                    modifier = Modifier
                        .border(
                            border = BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = R.color.primary_color),
                            ),
                            shape = CircleShape
                        )
                        .padding(10.dp)
                        .size(18.dp)
                        .clickable { /*TODO to go to notification screen*/ }
                        .align(Alignment.Center),
                )
            }
        },
    )
}