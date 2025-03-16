package com.minthanhtike.minflix.feature.detail.ui

import RatingStar
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.ExpandableText
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.common.TabItems
import com.minthanhtike.minflix.common.shimmerEffect
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailCasterCrewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRecommendModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRelatedInfoModels
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailReviewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailTrailerModel
import com.minthanhtike.minflix.feature.detail.ui.components.BackdropImage
import com.minthanhtike.minflix.feature.detail.ui.components.CasterCrewCard
import com.minthanhtike.minflix.feature.detail.ui.components.InfosLoading
import com.minthanhtike.minflix.feature.detail.ui.components.MyTabRow
import com.minthanhtike.minflix.feature.detail.ui.components.OverviewLoading
import com.minthanhtike.minflix.feature.detail.ui.components.getHomeDetailUiSize
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailRelatedInfos
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailUiState
import kotlinx.coroutines.flow.flowOf


private const val POSTERIMG = "posterImg"
private const val TITLETXT = "titleTxt"
private const val ABOUTTXT = "aboutMvTxt"
private const val OVERVIEWLOADING = "loadingDetail"
private const val INFOSLOADING = "loadingInfos"
private const val PRODUCTIONCOMPANY = "productionCompany"
private const val STARRATINGBAR = "ratingBar"
private const val VIDEO = "videoLink"

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeDetailMainPane(
    modifier: Modifier = Modifier,
    id: Int, name: String,
    image: String, type: String,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    homeDetailUiState: HomeDetailUiState,
    onTabSelect: (HomeDetailAction) -> Unit,
    tabData: List<TabItems>,
    homeDetailRelatedInfos: HomeDetailRelatedInfos
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
    val homeDetailRelatedInfoState =
        homeDetailRelatedInfos.homeDetailRelatedInfos.collectAsLazyPagingItems()

    val casterCrewList = homeDetailRelatedInfoState.itemSnapshotList.items
        .filterIsInstance<HomeDetailCasterCrewModel>()

    val reviewList = homeDetailRelatedInfoState.itemSnapshotList.items
        .filterIsInstance<HomeDetailReviewModel>()

    val recommendList = homeDetailRelatedInfoState.itemSnapshotList.items
        .filterIsInstance<HomeDetailRecommendModel>()

    val trailerList = homeDetailRelatedInfoState.itemSnapshotList.items
        .filterIsInstance<HomeDetailTrailerModel>()

    val overViewsText = remember { mutableStateOf("") }

    val getHomeDetailUiSize = getHomeDetailUiSize()
    val gridState = rememberLazyGridState()

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


    val isImgLoading by rememberSaveable(homeDetailUiState) {
        mutableStateOf(homeDetailUiState is HomeDetailUiState.Loading)
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        state = gridState
    ) {
        //region poster image,movie titles,rating bar and background image
        item(key = 1, span = { GridItemSpan(3) }) {
            AnimatedContent(
                targetState = homeDetailUiState, label = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) { homeDetailState ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        constraintSet = constraintSet
                    ) {
                        when (homeDetailState) {
                            is HomeDetailUiState.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(getHomeDetailUiSize.backdropImgHeight.dp)
                                        .layoutId(VIDEO)
                                        .shimmerEffect(true)
                                )
                                OverviewLoading(modifier = Modifier.layoutId(OVERVIEWLOADING))
                                InfosLoading(modifier = Modifier.layoutId(INFOSLOADING))
                            }

                            is HomeDetailUiState.Success -> {
                                val data = homeDetailState.homeDetailModel
                                BackdropImage(
                                    image = data.backdropImg,
                                    contentDes = data.title,
                                    isDetailUiLoading = isImgLoading,
                                    modifier = Modifier
                                        .layoutId(VIDEO)
                                        .fillMaxWidth()
                                        .height(getHomeDetailUiSize.backdropImgHeight.dp)
                                )

                                Text(
                                    text = data.companyName,
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
                                    rating = (data.rating / 2).toFloat(),
                                    modifier = Modifier.layoutId(STARRATINGBAR),
                                    onStarClick = {}
                                )


                                if (data.overView.isNotEmpty()) {
                                    overViewsText.value = data.overView
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
                            }

                            is HomeDetailUiState.Error -> {}
                            HomeDetailUiState.Idle -> {}
                        }


                        with(sharedTransitionScope) {
                            AsyncImage(
                                model = image,
                                contentDescription = "name",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .layoutId(POSTERIMG)
                                    .clip(RoundedCornerShape(15.dp))
                                    .sharedElement(
                                        state = sharedTransitionScope
                                            .rememberSharedContentState(key = "image-$id $type"),
                                        animatedVisibilityScope = animatedContentScope,
                                        clipInOverlayDuringTransition =
                                        OverlayClip(RoundedCornerShape(cornerSize.value)),
                                        boundsTransform = { initialBounds, targetBounds ->
                                            tween(
                                                durationMillis = 2000,
                                                easing = LinearOutSlowInEasing
                                            )
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

            }
        }
        //endregion

        //region about movie and images from the movies
        item(key = 2, span = { GridItemSpan(3) }) {
            Column {
                AnimatedContent(
                    targetState = homeDetailUiState,
                    label = "Animating images state",
                    transitionSpec = {
                        scaleIn(tween()).togetherWith(scaleOut(tween()))
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
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
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    contentPadding = PaddingValues(horizontal = 20.dp)
                                ) {
                                    items(
                                        items = detailUiState.homeDetailModel.imagesList
                                    ) { data ->
                                        SubcomposeAsyncImage(
                                            model = data,
                                            contentDescription = "",
                                            contentScale = ContentScale.FillBounds,
                                            loading = {
                                                Box(
                                                    modifier = Modifier
                                                        .size(250.dp, 150.dp)
                                                        .shimmerEffect(true)
                                                )
                                            },
                                            modifier = Modifier
                                                .padding(end = 15.dp, top = 10.dp)
                                                .size(250.dp, 150.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                    }

                                }
                            }
                        }

                        is HomeDetailUiState.Error -> {}

                        HomeDetailUiState.Idle -> {}
                    }
                }

                MyTabRow(
                    tabList = tabData,
                    modifier = Modifier
                        .padding(top = 30.dp, start = 30.dp, end = 20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    onTabSelect = onTabSelect
                )
            }
        }
        //endregion


        if (homeDetailRelatedInfoState.loadState.refresh is LoadState.Loading) {
            item(key = 3, span = { GridItemSpan(3) }) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),

                        )
                }
            }
        }


        if (
            homeDetailRelatedInfoState.loadState.refresh is LoadState.NotLoading
            && casterCrewList.isNotEmpty()
        ) {
            item(key = 4, span = { GridItemSpan(3) }) {

                Column(modifier = Modifier.padding(vertical = 15.dp)) {
                    //region release-date,production company,speak lang,budgets
                    if (homeDetailUiState is HomeDetailUiState.Success) {
                        FlowRow(
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            maxItemsInEachRow = 2,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            homeDetailUiState.homeDetailModel.spokenLang?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                ) {
                                    Text(
                                        text = "Spoken Languages",
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        ),
                                    )
                                    Text(
                                        text = it.joinToString(separator = ",") { it },
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(top = 6.dp),
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        )
                                    )
                                }
                            }

                            homeDetailUiState.homeDetailModel.releaseDate?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                ) {
                                    Text(
                                        text = "Release Date",
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        )
                                    )

                                    Text(
                                        text = it,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        ),
                                        modifier = Modifier.padding(top = 6.dp)
                                    )
                                }
                            }

                            homeDetailUiState.homeDetailModel.budget?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                ) {
                                    Text(
                                        text = "Budgets",
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        ),
                                    )
                                    Text(
                                        text = it,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(top = 6.dp),
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        )
                                    )
                                }
                            }

                            homeDetailUiState.homeDetailModel.homePage?.let {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                ) {
                                    Text(
                                        text = "Homepage",
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        ),
                                    )
                                    Text(
                                        text = it,
                                        fontSize = 14.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(top = 6.dp),
                                        fontFamily = FontFamily(
                                            Font(
                                                googleFont = FontProvider.fontNameInter,
                                                fontProvider = FontProvider.provider
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                    //endregion

                    Text(
                        text = "Casters and Crew",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 18.dp, start = 30.dp, end = 30.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(
                            Font(
                                googleFont = FontProvider.fontNameInter,
                                fontProvider = FontProvider.provider
                            )
                        )
                    )
                    //region caster and crew
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 30.dp)
                    ) {
                        val casterList = casterCrewList[0].cast
                        items(
                            count = casterList.size,
                            key = { casterList[it].creditId }
                        ) { index ->
                            CasterCrewCard(
                                img = casterList[index].profilePath,
                                contentDes = casterList[index].name,
                                name = casterList[index].name,
                                department = casterList[index].knownForDepartment,
                                modifier = Modifier
                                    .fillParentMaxWidth(0.4f)
                                    .aspectRatio(0.7f)
                                    .clip(RoundedCornerShape(6.dp)),
                            )
                        }

                        val crewList = casterCrewList[0].crew
                        items(
                            count = crewList.size,
                            key = { crewList[it].creditId }
                        ) { index ->
                            CasterCrewCard(
                                img = crewList[index].profilePath,
                                contentDes = crewList[index].name,
                                name = crewList[index].name,
                                department = crewList[index].knownForDepartment,
                                modifier = Modifier
                                    .fillParentMaxWidth(0.4f)
                                    .aspectRatio(0.7f)
                                    .clip(RoundedCornerShape(6.dp)),
                            )
                        }

                    }
                    //endregion
                }

            }
        }

        if (
            homeDetailRelatedInfoState.loadState.refresh is LoadState.NotLoading
            && reviewList.isNotEmpty()
        ) {
            items(
                count = reviewList.size,
                span = { GridItemSpan(3) },
            ) { index ->
                Column(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 15.dp)
                        .fillMaxWidth()
                ) {
                    Row {
                        SubcomposeAsyncImage(
                            model = reviewList[index].authorDetails.avatarPath,
                            contentDescription = reviewList[index].author,
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .shimmerEffect(true)
                                )
                            },
                            error = {
                                Box(
                                    modifier = Modifier
                                        .background(color = Color.DarkGray)
                                )
                            },
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = reviewList[index].author,
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily(
                                Font(
                                    googleFont = FontProvider.fontNameInter,
                                    fontProvider = FontProvider.provider
                                )
                            ),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                        )
                    }

                    ExpandableText(
                        text = reviewList[index].content,
                        showMoreStyle = SpanStyle(
                            fontWeight = FontWeight.Thin,
                            color = colorResource(id = R.color.primary_color)
                        ),
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
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth()
                    )
                    AnimatedVisibility(visible = index != reviewList.lastIndex) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.LightGray,
                        )
                    }

                }
            }
        }

        if (
            homeDetailRelatedInfoState.loadState.refresh is LoadState.NotLoading
            && recommendList.isNotEmpty()
        ) {
            Log.wtf("recommendLists", "$recommendList")
            items(items = recommendList) {

            }
        }

        if (
            homeDetailRelatedInfoState.loadState.refresh is LoadState.NotLoading
            && trailerList.isNotEmpty()
        ) {
            Log.wtf("trailer", "${trailerList.size}")
            items(trailerList, span = { GridItemSpan(3) }) {

            }
        }

        if (homeDetailRelatedInfoState.loadState.hasError) {
            item { }
        }

    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun PrevHomeDetailMainPane() {
    SharedTransitionLayout(modifier = Modifier.background(Color.Black)) {
        AnimatedContent(targetState = false, label = "") { s ->
            s
            HomeDetailMainPane(
                id = 0,
                name = "min than htike",
                image = "", type = "",
                animatedContentScope = this,
                sharedTransitionScope = this@SharedTransitionLayout,
                homeDetailUiState = HomeDetailUiState.Success(
                    HomeDetailModel(
                        title = "", rating = 0.0,
                        companyName = "", backdropImg = "", overView = "",
                        imagesList = emptyList(),
                        releaseDate = "12-3-2001",
                        spokenLang = listOf("English"),
                        homePage = "https://www.home.com",
                        productionCompany = listOf("Universal Pictures", "Sony Pictures"),
                        budget = "3Millions"
                    )
                ),
                tabData = TabItems.entries,
                onTabSelect = {},
                homeDetailRelatedInfos = HomeDetailRelatedInfos(
                    homeDetailRelatedInfos = flowOf(
                        PagingData.from(
                            listOf(
                                HomeDetailCasterCrewModel(
                                    cast = listOf(
                                        HomeDetailCasterCrewModel.Cast(
                                            castId = 1,
                                            character = "Bruce Wayne / Batman",
                                            creditId = "ca123",
                                            gender = 1,
                                            id = 201,
                                            knownForDepartment = "Acting",
                                            name = "Christian Bale",
                                            originalName = "Christian Bale",
                                            popularity = 10.0,
                                            profilePath = "/path/to/christian_bale.jpg"
                                        )
                                    ),
                                    crew = listOf(
                                        HomeDetailCasterCrewModel.Crew(
                                            creditId = "cr123",
                                            department = "Directing",
                                            gender = 1,
                                            id = 101,
                                            job = "Director",
                                            knownForDepartment = "Directing",
                                            name = "Christopher Nolan",
                                            originalName = "Christopher Nolan",
                                            popularity = 9.8,
                                            profilePath = "/path/to/christopher_nolan.jpg"
                                        )
                                    )
                                ) as HomeDetailRelatedInfoModels
                            ),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false)
                            )
                        )
                    )
                )
            )
        }
    }
}

