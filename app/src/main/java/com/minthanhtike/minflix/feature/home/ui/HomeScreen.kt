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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.feature.home.ui.component.ItemHeader
import com.minthanhtike.minflix.feature.home.ui.component.MessageCard
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
    trendTvOnClick: (id: String, name: String, image: String) -> Unit
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
        retryApiCall = { time ->
            homeViewModel.getTrendingMovie(time)
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
    retryApiCall: (str: String) -> Unit,
    onTrendMovieDateSelect: (date: String) -> Unit,
    onTrendTvDateSelect: (date: String) -> Unit,
    trendTvOnClick: (id: String, name: String, image: String) -> Unit
) {
    var trendMovieTime by remember {
        mutableStateOf("day")
    }
    val trendingMovieState by uiState.trendingMovieState
        .collectAsStateWithLifecycle(initialValue = TrendingMovieState.Idle)

    val trendTvs = uiState.trendingTvState.collectAsLazyPagingItems()
    val nowPlayMovies = uiState.nowPlayMoviesState.collectAsLazyPagingItems()
    val todayAiringTvs = uiState.getAirTvTodayState.collectAsLazyPagingItems()

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

    var trendMoviesListSize by remember { mutableIntStateOf(0) }

    val nowPlayState = when {
        (nowPlayMovies.loadState.refresh is LoadState.NotLoading &&
                nowPlayMovies.itemSnapshotList.items.isNotEmpty()) -> "success"

        nowPlayMovies.loadState.hasError -> "error"
        else -> "loading"
    }

    val trendLazyRowState =
        when {
            (trendTvs.loadState.refresh is LoadState.NotLoading &&
                    trendTvs.itemSnapshotList.items.isNotEmpty()) -> "success"

            trendTvs.loadState.hasError -> "error"
            else -> "loading"
        }

    val airRowState =
        when {
            (todayAiringTvs.loadState.refresh is LoadState.NotLoading &&
                    todayAiringTvs.itemSnapshotList.items.isNotEmpty()) -> "success"

            todayAiringTvs.loadState.hasError -> "error"
            else -> "loading"
        }

    val pagerState = rememberPagerState(pageCount = { trendMoviesListSize })
    if (pagerState.pageCount != 0) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(3500)
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(
                    page = nextPage, animationSpec = tween()
                )
            }
        }
    }

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
                trendMovieTime = date
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
            if (trendingMovieState is TrendingMovieState.Error) {
                MessageCard(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Oops!Something went wrong",
                    buttonText = "Try again",
                    onClick = dropUnlessResumed {
                        retryApiCall(trendMovieTime)
                    }
                )
            }

            if (trendingMovieState is TrendingMovieState.Success) {
                val trendingMovieList =
                    (trendingMovieState as TrendingMovieState.Success).trendMovie
                trendMoviesListSize = trendingMovieList.size

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
                        AsyncImage(
                            model = trendingMovieList[currentPage].backdropPath,
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

                AnimatedContent(
                    targetState = trendLazyRowState,
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
                    if (targetState == "success") {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(trendTvs.itemCount) { currentItem ->
                                trendTvs[currentItem]?.let { tv ->
                                    PosterCard(
                                        id = "$currentItem trendTv",
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
                                                "$currentItem trendTv",
                                                tv.name,
                                                tv.posterPath
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                    if (targetState == "loading") {
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
                    if (targetState == "error") {
                        MessageCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Oop!Something went wrong",
                            buttonText = "Try again",
                            onClick = dropUnlessResumed { trendTvs.retry() }
                        )
                    }
                }

            }

        }
        //endregion

        //region now playing movies
        item(key = 4) {
            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
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

                AnimatedContent(
                    targetState = nowPlayState,
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
                    if (targetState == "success") {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(nowPlayMovies.itemCount) { currentItem ->
                                nowPlayMovies[currentItem]?.let { movies ->
                                    PosterCard(
                                        id = "$currentItem nowPlayMovie",
                                        posterImg = movies.posterPath,
                                        onClick = {
                                            trendTvOnClick(
                                                "$currentItem nowPlayMovie",
                                                movies.title,
                                                movies.posterPath
                                            )
                                        },
                                        name = movies.title,
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(210.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .animateItem(),
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedContentScope = animatedContentScope,
                                    )
                                }
                            }
                        }
                    }
                    if (nowPlayState == "loading") {
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
                    if (targetState == "error") {
                        MessageCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Oop!Something went wrong",
                            buttonText = "Try again",
                            onClick = dropUnlessResumed { nowPlayMovies.retry() }
                        )
                    }

                }
            }
        }
        //endregion

        //region Airing today
        item(key = 5) {
            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillParentMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Tv Shows Airing Today",
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

                AnimatedContent(
                    targetState = airRowState,
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
                    if (targetState == "success") {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(todayAiringTvs.itemCount) { currentItem ->
                                todayAiringTvs[currentItem]?.let { tv ->
                                    PosterCard(
                                        id = "$currentItem airingTv",
                                        posterImg = tv.posterPath,
                                        onClick = {
                                            trendTvOnClick(
                                                "$currentItem airingTv",
                                                tv.name,
                                                tv.posterPath
                                            )
                                        },
                                        name = tv.name,
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(210.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .animateItem(),
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedContentScope = animatedContentScope,
                                    )
                                }
                            }
                        }
                    }

                    if (targetState == "loading") {
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

                    if (targetState == "error") {
                        MessageCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Oop!Something went wrong",
                            buttonText = "Try again",
                            onClick = dropUnlessResumed { todayAiringTvs.retry() }
                        )
                    }
                }
            }
        }
        //endregion

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
                trendTvOnClick = { _, _, _ -> },
                retryApiCall = {}
            )
        }
    }
}