package com.minthanhtike.minflix.feature.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.feature.home.ui.component.ItemHeader
import com.minthanhtike.minflix.feature.home.ui.component.PageIndicator
import com.minthanhtike.minflix.feature.home.ui.component.PosterCard
import com.minthanhtike.minflix.ui.component.LocalAnimatedContentScope
import com.minthanhtike.minflix.ui.component.LocalSharedTransitionScope
import com.minthanhtike.minflix.ui.theme.MinFlixTheme
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

private const val TRENDMVTXT = "TrendMovieTxt"
private const val TRENDSWITCH = "TrendSwitch"
private const val TRENDTVTXT = "TrendTvTxt"
private const val TRENDTVSWITCH = "TrendTvSwitch"


@Composable
fun HomeScn(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel(),
    trendTvOnClick: (id: Int, name: String, image: String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeScnContent(
        uiState = uiState,
        paddingValues = paddingValues,
        onTrendMovieDateSelect = { date ->
            homeViewModel.getTrendingMovie(date)
        },
        onTrendTvDateSelect = { date ->
            homeViewModel.getTrendingTv(date)
        },
        trendTvOnClick = trendTvOnClick
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScnContent(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onTrendMovieDateSelect: (date: String) -> Unit,
    onTrendTvDateSelect: (date: String) -> Unit,
    trendTvOnClick: (id: Int, name: String, image: String) -> Unit
) {
    val trendingMovieState by uiState.trendingMovieState
        .collectAsStateWithLifecycle(initialValue = TrendingMovieState.Idle)

    val trendTvState = uiState.trendingTvState.collectAsLazyPagingItems()
    val nowPlayMovieState = uiState.nowPlayMoviesState.collectAsLazyPagingItems()

    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current

    val trendMovieConstraintSet = ConstraintSet {
        val trendTxtRef = createRefFor(TRENDMVTXT)
        val trendSwitchRef = createRefFor(TRENDSWITCH)

        constrain(trendTxtRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }

        constrain(trendSwitchRef) {
            top.linkTo(trendTxtRef.top)
            end.linkTo(parent.end)
            bottom.linkTo(trendTxtRef.bottom)
        }
    }

    val trendTvConstraintSet = ConstraintSet {
        val trendTxtRef = createRefFor(TRENDTVTXT)
        val trendSwitchRef = createRefFor(TRENDTVSWITCH)

        constrain(trendTxtRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }

        constrain(trendSwitchRef) {
            top.linkTo(trendTxtRef.top)
            end.linkTo(parent.end)
            bottom.linkTo(trendTxtRef.bottom)
        }
    }

    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(top = 25.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = 100.dp,
            bottom = 80.dp
        )
    ) {
        //region trending movies text and today and this week switch btn
        item(key = 1) {
            ItemHeader(
                trendMovieConstraintSet = trendMovieConstraintSet,
                headerText = "Trending Movies",
                headerTextId = TRENDMVTXT,
                daySwitchId = TRENDSWITCH,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .wrapContentHeight()
            ) { date ->
                onTrendMovieDateSelect(date)
            }
        }
        //endregion

        //region auto image slider using horizontal pager
        item(key = 2) {
            if (trendingMovieState is TrendingMovieState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.primary_color),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (trendingMovieState is TrendingMovieState.Success) {
                val trendingMovieList =
                    (trendingMovieState as TrendingMovieState.Success).trendMovie
                val pagerState = rememberPagerState(pageCount = { trendingMovieList.size })
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(3500)
                        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                        pagerState.animateScrollToPage(
                            page = nextPage, animationSpec = tween()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(240.dp)
                        .padding(top = 20.dp)
                ) {
                    HorizontalPager(
                        pageSize = PageSize.Fill,
                        userScrollEnabled = true,
                        pageSpacing = 10.dp,
                        state = pagerState,
                        flingBehavior = PagerDefaults.flingBehavior(
                            state = pagerState,
                            pagerSnapDistance = PagerSnapDistance.atMost(0)
                        ),
                        contentPadding = when (pagerState.currentPage) {
                            0 -> PaddingValues(end = 32.dp, start = 20.dp)
                            6 -> PaddingValues(start = 32.dp, end = 20.dp)
                            else -> PaddingValues(horizontal = 32.dp)
                        },
                    ) { currentPage ->
                        val currentMovie = trendingMovieList[currentPage]
                        AsyncImage(
                            model = currentMovie.backdropPath,
                            contentDescription = "Trending Movies Image",
                            contentScale = ContentScale.Companion.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .graphicsLayer {
                                    val pageOffSet =
                                        ((pagerState.currentPage - currentPage) + pagerState.currentPageOffsetFraction).absoluteValue
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                                    )
                                    scaleY = lerp(
                                        start = 0.75f,
                                        stop = 1f,
                                        fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                                    )
                                }
                        )
                    }

                    PageIndicator(
                        pageCount = trendingMovieList.size,
                        currentPage = pagerState.currentPage,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .wrapContentSize()
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

        }
        //endregion

        //region trending tv
        item(key = 3) {
            Column(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .wrapContentHeight()
            ) {
                ItemHeader(
                    trendMovieConstraintSet = trendTvConstraintSet,
                    headerText = "Trending Tv Show",
                    headerTextId = TRENDTVTXT,
                    daySwitchId = TRENDTVSWITCH,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillParentMaxWidth()
                        .wrapContentHeight()
                ) { onTrendTvDateSelect(it) }

                val showLazyRow = trendTvState.loadState.refresh is LoadState.NotLoading &&
                        trendTvState.itemSnapshotList.items.isNotEmpty()

                AnimatedContent(
                    targetState = showLazyRow,
                    label = "Animating Lazy Row",
                    transitionSpec = {
                        (fadeIn(
                            animationSpec = tween(220, delayMillis = 90)
                        ) + scaleIn(
                            initialScale = 0.92f,
                            animationSpec = tween(220, delayMillis = 90)
                        )).togetherWith(fadeOut(animationSpec = tween(90)))
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .wrapContentSize()
                ) { targetState ->
                    if (targetState) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(trendTvState.itemCount) { currentItem ->
                                trendTvState[currentItem]?.let { tv ->
                                    PosterCard(
                                        id = currentItem,
                                        posterImg = tv.posterPath,
                                        name = tv.name,
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(210.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .animateItem(),
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedContentScope = animatedContentScope,
                                        onClick = {
                                            trendTvOnClick(
                                                currentItem,
                                                tv.name,
                                                tv.posterPath
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(220.dp)
                        ) {
                            CircularProgressIndicator(
                                color = colorResource(id = R.color.primary_color),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

            }

        }
        //endregion


        item(key = 4) {
            Column(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Now Playing Movies",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(
                        Font(
                            googleFont = FontProvider.fontNameInter,
                            fontProvider = FontProvider.provider
                        )
                    ),
                    modifier = Modifier.padding(start = 20.dp)
                )
                val showLazyRow = nowPlayMovieState.loadState.refresh is LoadState.NotLoading &&
                        nowPlayMovieState.itemSnapshotList.items.isNotEmpty()

                AnimatedContent(
                    targetState = showLazyRow,
                    label = "Animating Lazy Row",
                    transitionSpec = {
                        (fadeIn(
                            animationSpec = tween(220, delayMillis = 90)
                        ) + scaleIn(
                            initialScale = 0.92f,
                            animationSpec = tween(220, delayMillis = 90)
                        )).togetherWith(fadeOut(animationSpec = tween(90)))
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .wrapContentSize()
                ) { targetState ->
                    if (targetState) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(nowPlayMovieState.itemCount) { currentItem ->
                                Card(
                                    modifier = Modifier
                                        .width(140.dp)
                                        .height(210.dp),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    AsyncImage(
                                        model = nowPlayMovieState[currentItem]?.posterPath.orEmpty(),
                                        contentDescription = "Tv Show PosterPath",
                                        modifier = Modifier.fillParentMaxSize(),
                                        contentScale = ContentScale.Fit,
                                        onState = { state ->
                                            when (state) {
                                                is AsyncImagePainter.State.Loading -> {}
                                                is AsyncImagePainter.State.Error -> {}
                                                is AsyncImagePainter.State.Empty -> {}
                                                is AsyncImagePainter.State.Success -> {}
                                            }

                                        }
                                    )

                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(220.dp)
                        ) {
                            CircularProgressIndicator(
                                color = colorResource(id = R.color.primary_color),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

        }


    }
}


@Preview
@Composable
private fun HomeScnPrev() {
    MinFlixTheme {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.bg_img),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .background(Color(0, 0, 0, 228))
                    .fillMaxSize()
            )
            HomeScnContent(
                paddingValues = PaddingValues(0.dp),
                onTrendMovieDateSelect = {},
                uiState = HomeUiState(),
                onTrendTvDateSelect = {},
                trendTvOnClick = { _, _, _ -> }
            )
        }
    }
}