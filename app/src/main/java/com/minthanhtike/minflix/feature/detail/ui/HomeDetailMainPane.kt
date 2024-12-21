package com.minthanhtike.minflix.feature.detail.ui

import RatingStar
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.ExpandableText
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.common.ShimmerEffect
import com.minthanhtike.minflix.common.TabItems
import com.minthanhtike.minflix.common.currentWindowSizeInfo
import com.minthanhtike.minflix.common.shimmerEffect
import com.minthanhtike.minflix.feature.detail.domain.model.MovieDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.MovieImagesModel
import com.minthanhtike.minflix.feature.detail.domain.model.TvDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.TvImagesModel
import com.minthanhtike.minflix.feature.detail.ui.components.CustomTabRow
import com.minthanhtike.minflix.feature.detail.ui.components.InfosLoading
import com.minthanhtike.minflix.feature.detail.ui.components.OverviewLoading


private const val POSTERIMG = "posterImg"
private const val TITLETXT = "titleTxt"
private const val ABOUTTXT = "aboutMvTxt"
private const val OVERVIEWLOADING = "loadingDetail"
private const val INFOSLOADING = "loadingInfos"
private const val PRODUCTIONCOMPANY = "productionCompany"
private const val STARRATINGBAR = "ratingBar"
private const val VIDEO = "videoLink"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeDetailMainPane(
    modifier: Modifier = Modifier,
    id: Int, name: String,
    image: String, type: String,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    homeDetailUiState: HomeDetailUiState,
    onTabSelect: (String) -> Unit,
    tabData: List<TabItems>
) {

    val constraintSet = ConstraintSet {
        val posterImgRef = createRefFor(POSTERIMG)
        val titleTxtRef = createRefFor(TITLETXT)
        val aboutTxtRef = createRefFor(ABOUTTXT)
        val overViewLoading = createRefFor(OVERVIEWLOADING)
        val infosLoading = createRefFor(INFOSLOADING)
        val productionTxt = createRefFor(PRODUCTIONCOMPANY)
        val ratingBar = createRefFor(STARRATINGBAR)
        val video = createRefFor(VIDEO)

        constrain(ref = video) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }

        constrain(ref = posterImgRef) {
            width = Dimension.value(140.dp)
            height = Dimension.value(210.dp)
            top.linkTo(video.bottom)
            start.linkTo(parent.start, 20.dp)
            bottom.linkTo(video.bottom)
        }

        constrain(ref = titleTxtRef) {
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
            bottom.linkTo(
                anchor = if (homeDetailUiState is HomeDetailUiState.Loading)
                    infosLoading.top else productionTxt.top,
                margin = 1.5.dp
            )
            start.linkTo(posterImgRef.end, margin = 8.dp)
            end.linkTo(parent.end, margin = 10.dp)
        }

        constrain(ref = aboutTxtRef) {
            width = Dimension.wrapContent
            height = Dimension.wrapContent
            top.linkTo(posterImgRef.bottom, margin = 15.dp)
            start.linkTo(posterImgRef.start, margin = 2.dp)
        }


        constrain(ref = overViewLoading) {
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
            top.linkTo(posterImgRef.bottom, margin = 15.dp)
            start.linkTo(posterImgRef.start, margin = 2.dp)
            end.linkTo(parent.end, margin = 20.dp)
        }

        constrain(ref = infosLoading) {
            start.linkTo(titleTxtRef.start)
            bottom.linkTo(posterImgRef.bottom, margin = 1.2.dp)
        }

        constrain(ref = productionTxt) {
            width = Dimension.wrapContent
            height = Dimension.wrapContent
            bottom.linkTo(ratingBar.top, margin = 1.2.dp)
            start.linkTo(titleTxtRef.start)
        }

        constrain(ref = ratingBar) {
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
            bottom.linkTo(posterImgRef.bottom, margin = 1.2.dp)
            start.linkTo(titleTxtRef.start)
            end.linkTo(parent.end, 15.dp)
        }
    }

    val windowSize = currentWindowSizeInfo()
    val overViewsText = remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf("") }

    val cornerSize =
        animatedContentScope.transition.animateDp(
            label = "corner",
            transitionSpec = {
                tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
            }
        ) { exitEnter ->
            when (exitEnter) {
                EnterExitState.PreEnter -> 16.dp
                EnterExitState.Visible -> 24.dp
                EnterExitState.PostExit -> 16.dp
            }
        }

    val img = if (homeDetailUiState is HomeDetailUiState.Success) {
        when (val data = homeDetailUiState.homeDetailModel) {
            is TvDetailModel -> data.backdropPath
            is MovieDetailModel -> data.backdropPath
        }
    } else {
        ""
    }

    var isImgLoading by rememberSaveable {
        mutableStateOf(false)
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //region poster image movie titles and background image
        item(key = 1) {
            Column(
                modifier = Modifier.wrapContentSize()
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    constraintSet = constraintSet
                ) {
                    AsyncImage(
                        model = img,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        onSuccess = { isImgLoading = false },
                        onLoading = { isImgLoading = true },
                        modifier = Modifier
                            .layoutId(VIDEO)
                            .fillMaxWidth()
                            .fillParentMaxHeight(
                                if ((windowSize.widthSizeClass == WindowWidthSizeClass.Medium) or
                                    (windowSize.widthSizeClass == WindowWidthSizeClass.Expanded)
                                ) {
                                    0.45f
                                } else {
                                    0.35f
                                }
                            )
                            .shimmerEffect(
                                homeDetailUiState is HomeDetailUiState.Loading ||
                                        isImgLoading
                            )
                            .graphicsLayer { alpha = 1f }
                            .drawWithContent {
                                val colors =
                                    listOf(
                                        Color.Transparent,
                                        Color.Black
                                    )
                                drawContent()
                                drawRect(
                                    brush = Brush.linearGradient(
                                        colors = colors,
                                        start = Offset(0f, size.height / 2), // Start at the middle
                                        end = Offset(0f, size.height) // End at the bottom
                                    ),
                                    topLeft = Offset(
                                        0f,
                                        size.height / 2
                                    ), // Rectangle starts halfway
                                    size = Size(size.width, size.height / 2) // Half of the height
                                )
                            }
                    )
                    with(sharedTransitionScope) {
                        AsyncImage(
                            model = image,
                            contentDescription = "name",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .background(Color.DarkGray)
                                .layoutId(POSTERIMG)
                                .clip(RoundedCornerShape(15.dp))
                                .sharedElement(
                                    state = sharedTransitionScope
                                        .rememberSharedContentState(key = "image-$id $type"),
                                    animatedVisibilityScope = animatedContentScope,
                                    clipInOverlayDuringTransition =
                                    OverlayClip(RoundedCornerShape(cornerSize.value)),
                                    boundsTransform = { initialBounds, targetBounds ->
                                        tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
                                    },
                                )
                        )
                        //region title text
                        Text(
                            color = Color.White,
                            text = name,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .layoutId(TITLETXT)
                                .sharedElement(
                                    state = sharedTransitionScope
                                        .rememberSharedContentState(key = "text-$id $type"),
                                    animatedVisibilityScope = animatedContentScope
                                )
                        )
                        //endregion
                    }

                    if (homeDetailUiState is HomeDetailUiState.Loading) {
                        OverviewLoading(modifier = Modifier.layoutId(OVERVIEWLOADING))
                        InfosLoading(modifier = Modifier.layoutId(INFOSLOADING))
                    }

                    if (homeDetailUiState is HomeDetailUiState.Success) {
                        Text(
                            text = when (val data = homeDetailUiState.homeDetailModel) {
                                is TvDetailModel -> data.productionCompany
                                is MovieDetailModel -> data.productionCompany
                            },
                            modifier = Modifier.layoutId(PRODUCTIONCOMPANY),
                            style = LocalTextStyle.current.merge(
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(
                                    Font(
                                        googleFont = FontProvider.fontNameInter,
                                        fontProvider = FontProvider.provider
                                    )
                                )
                            )
                        )

                        RatingStar(
                            rating = when (val data = homeDetailUiState.homeDetailModel) {
                                is TvDetailModel -> (data.voteAverage / 2).toFloat()
                                is MovieDetailModel -> (data.voteAverage / 2).toFloat()
                            },
                            modifier = Modifier.layoutId(STARRATINGBAR),
                            onStarClick = {}
                        )

                        //region about movies text
                        val overViewTxt = when (val data = homeDetailUiState.homeDetailModel) {
                            is TvDetailModel -> data.overview
                            is MovieDetailModel -> data.overview
                        }
                        if (overViewTxt.isNotEmpty()) {
                            overViewsText.value = overViewTxt
                            Text(
                                text = "Overview",
                                fontSize = 15.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(
                                    Font(
                                        googleFont = FontProvider.fontNameInter,
                                        fontProvider = FontProvider.provider
                                    )
                                ),
                                modifier = Modifier.layoutId(ABOUTTXT)
                            )
                        }
                        //endregion
                    }

                }
            }
            ExpandableText(
                text = overViewsText.value,
                fontSize = 13.sp,
                collapsedMaxLine = 4,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    ),
                    lineBreak = LineBreak.Paragraph
                ),
                fontFamily = FontFamily(
                    Font(
                        googleFont = FontProvider.fontNameInter,
                        fontProvider = FontProvider.provider
                    )
                ),
                modifier = Modifier.padding(horizontal = 22.dp)
            )
        }
        //endregion

        //region about movie and images from the movies
        item(key = 2) {
            AnimatedContent(
                targetState = homeDetailUiState,
                label = "Animating images state",
                transitionSpec = {
                    scaleIn(tween()).togetherWith(scaleOut(tween()))
                },
                modifier = Modifier
                    .padding(top = 20.dp)
            ) { detailUiState ->
                when (detailUiState) {
                    is HomeDetailUiState.Loading -> {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(2) {
                                Box(
                                    modifier = Modifier
                                        .size(250.dp, 150.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .shimmerEffect(true)
                                )
                            }
                        }
                    }

                    is HomeDetailUiState.Success -> {
                        var isLoading by rememberSaveable {
                            mutableStateOf(false)
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = if (type.contains("Tv", true)) {
                                    "Images From Tv Shows"
                                } else {
                                    "Images From Movies"
                                },
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(
                                    Font(
                                        googleFont = FontProvider.fontNameInter,
                                        fontProvider = FontProvider.provider
                                    )
                                ),
                                modifier = Modifier
                                    .padding(start = 22.dp)
                                    .wrapContentSize()
                            )
                            LazyRow(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .wrapContentSize(),
                                contentPadding = PaddingValues(horizontal = 20.dp)
                            ) {
                                items(
                                    items = when(detailUiState.homeDetailModel){
                                        is TvDetailModel -> detailUiState.homeDetailModel.tvImagesModel
                                        is MovieDetailModel -> detailUiState.homeDetailModel.movieImagesModel
                                    }
                                ) { data ->
                                    AsyncImage(
                                        model = when (data) {
                                            is TvImagesModel -> data.filePath
                                            is MovieImagesModel -> data.filePath
                                            else -> {""}
                                        },
                                        contentDescription = "",
                                        contentScale = ContentScale.FillBounds,
                                        onLoading = { isLoading = true },
                                        onSuccess = { isLoading = false },
                                        error = painterResource(id = R.drawable.ic_launcher_background),
                                        modifier = Modifier
                                            .padding(end = 15.dp, top = 10.dp)
                                            .size(250.dp, 150.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .shimmerEffect(isLoading = isLoading)
                                    )
                                }

                            }
                        }
                    }

                    is HomeDetailUiState.Error -> {}

                    HomeDetailUiState.Idle -> {}
                }
            }
        }
        //endregion

        //region tab bar
        item(key = 3) {

            CustomTabRow(
                tabList = tabData,
                modifier = Modifier
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onTabSelect = {
                    selectedTab = it
                }
            )
            //endregion
        }
        //endregion


    }
}


@Preview(showBackground = true)
@Composable
private fun PrevHomeDetailMainPane() {

}

