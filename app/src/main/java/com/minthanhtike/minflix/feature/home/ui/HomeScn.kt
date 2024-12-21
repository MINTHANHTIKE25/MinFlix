package com.minthanhtike.minflix.feature.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.common.FontProvider
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import com.minthanhtike.minflix.feature.home.ui.component.ItemHeader
import com.minthanhtike.minflix.feature.home.ui.component.MessageCard
import com.minthanhtike.minflix.feature.home.ui.component.PageIndicator
import com.minthanhtike.minflix.feature.home.ui.component.PosterCard
import com.minthanhtike.minflix.feature.home.ui.component.getHomeUiSize
import com.minthanhtike.minflix.ui.component.LocalAnimatedContentScope
import com.minthanhtike.minflix.ui.component.LocalSharedTransitionScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.absoluteValue

private const val TRENDMVTXT = "TrendMovieTxt"
private const val TRENDSWITCH = "TrendSwitch"
private const val TRENDTVTXT = "TrendTvTxt"
private const val TRENDTVSWITCH = "TrendTvSwitch"


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScn(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel(),
    trendTvOnClick: (id: Int, name: String, image: String, type: String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current
    HomeScnContent(
        uiState = uiState,
        paddingValues = paddingValues,
        onTrendMovieDateSelect = { date ->
            homeViewModel.getTrendingMovie(date)
        },
        onTrendTvDateSelect = { date ->
            homeViewModel.getTrendingTv(date)
        },
        trendTvOnClick = trendTvOnClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope
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
    trendTvOnClick: (id: Int, name: String, image: String, type: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    var trendMovieTime by remember {
        mutableStateOf("day")
    }
    val trendingMovieState by uiState.trendingMovieState
        .collectAsStateWithLifecycle(initialValue = TrendingMovieState.Idle)

    val trendTvs = uiState.trendingTvState.collectAsLazyPagingItems()
    val nowPlayMovies = uiState.nowPlayMoviesState.collectAsLazyPagingItems()
    val todayAiringTvs = uiState.getAirTvTodayState.collectAsLazyPagingItems()

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

    var trendMoviesListSize by rememberSaveable { mutableIntStateOf(0) }

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


    val homeUiSize = getHomeUiSize()

    val pagerState = rememberPagerState(pageCount = { trendMoviesListSize })
    if (pagerState.pageCount != 0) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(3000)
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
                        .fillParentMaxWidth()
                        .fillParentMaxHeight(homeUiSize.pagerHeight)
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
                        (trendingMovieState as TrendingMovieState.Error).retryApiCall(trendMovieTime)
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
                        .fillParentMaxHeight(homeUiSize.pagerHeight)
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
                            0 -> PaddingValues(
                                end = homeUiSize.pagerContentPadding.firstItemPaddingEnd.dp,
                                start = homeUiSize.pagerContentPadding.firstItemPaddingStart.dp
                            )

                            6 -> PaddingValues(
                                start = homeUiSize.pagerContentPadding.lastItemPaddingStart.dp,
                                end = homeUiSize.pagerContentPadding.lastItemPaddingEnd.dp
                            )

                            else -> PaddingValues(
                                horizontal = homeUiSize.pagerContentPadding
                                    .horizontalPadding.dp
                            )
                        },
                    ) { currentPage ->
                        AsyncImage(
                            model = trendingMovieList[currentPage].backdropPath,
                            contentDescription = "Trending Movies Image",
                            contentScale = ContentScale.Companion.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(homeUiSize.pagerItemHeight)
                                .graphicsLayer {
                                    val pageOffSet = ((pagerState.currentPage - currentPage) +
                                            (pagerState.currentPageOffsetFraction)).absoluteValue
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                                    )
                                    scaleY = lerp(
                                        start = 0.8f,
                                        stop = 1f,
                                        fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                                    )
                                }
                                .background(Color.LightGray)
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
                                .height((homeUiSize.posterCardSize.height + 50).dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(trendTvs.itemCount) { currentItem ->
                                trendTvs[currentItem]?.let { tv ->
                                    PosterCard(
                                        id = "${tv.id} trendTv",
                                        posterImg = tv.posterPath,
                                        textWidth = homeUiSize.posterCardSize.width,
                                        name = tv.name,
                                        modifier = Modifier
                                            .width(homeUiSize.posterCardSize.width.dp)
                                            .height(homeUiSize.posterCardSize.height.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .animateItem(),
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedContentScope = animatedContentScope,
                                        onClick = {
                                            trendTvOnClick(
                                                tv.id,
                                                tv.name,
                                                tv.posterPath,
                                                "trendTv"
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
                                .height((homeUiSize.posterCardSize.height + 50).dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(nowPlayMovies.itemCount) { currentItem ->
                                nowPlayMovies[currentItem]?.let { movies ->
                                    PosterCard(
                                        id = "${movies.id} nowPlayMovie",
                                        textWidth = homeUiSize.posterCardSize.width,
                                        posterImg = movies.posterPath,
                                        onClick = {
                                            trendTvOnClick(
                                                movies.id,
                                                movies.title,
                                                movies.posterPath,
                                                "nowPlayMovie"
                                            )
                                        },
                                        name = movies.title,
                                        modifier = Modifier
                                            .width(homeUiSize.posterCardSize.width.dp)
                                            .height(homeUiSize.posterCardSize.height.dp)
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
                                .height((homeUiSize.posterCardSize.height + 50).dp),
                            contentPadding = PaddingValues(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(todayAiringTvs.itemCount) { currentItem ->
                                todayAiringTvs[currentItem]?.let { tv ->
                                    PosterCard(
                                        id = "${tv.id} airingTv",
                                        posterImg = tv.posterPath,
                                        textWidth = homeUiSize.posterCardSize.width,
                                        onClick = {
                                            trendTvOnClick(
                                                tv.id,
                                                tv.name,
                                                tv.posterPath,
                                                "airingTv"
                                            )
                                        },
                                        name = tv.name,
                                        modifier = Modifier
                                            .width(homeUiSize.posterCardSize.width.dp)
                                            .height(homeUiSize.posterCardSize.height.dp)
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


@OptIn(ExperimentalSharedTransitionApi::class)
@PreviewScreenSizes
@Composable
private fun HomeScnPrev() {
    SharedTransitionLayout {
        AnimatedContent(targetState = false, label = "") { s ->
            s
            Box {
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
                    uiState = HomeUiState(
                        trendingTvState = MutableStateFlow(
                            PagingData.from(
                                data = listOf(
                                    TrendingTvModels(
                                        adult = false,
                                        backdropPath = "/sample_backdrop_path.jpg",
                                        firstAirDate = "2023-12-01",
                                        genreIds = listOf(18, 10765), // Example: Drama, Sci-Fi
                                        id = 56789,
                                        mediaType = "tv",
                                        name = "Sample TV Show",
                                        originCountry = listOf("US"),
                                        originalLanguage = "en",
                                        originalName = "Original Sample Name",
                                        overview = "This is a sample overview for a trending TV show.",
                                        popularity = 87.65,
                                        posterPath = "/sample_poster_path.jpg",
                                        voteAverage = 8.3,
                                        voteCount = 678
                                    )
                                )
                            )
                        ),
                        trendingMovieState = MutableStateFlow(
                            TrendingMovieState.Success(
                                trendMovie = buildList<TrendingMovieModels> {
                                    repeat(3) {
                                        add(
                                            TrendingMovieModels(
                                                adult = false,
                                                backdropPath = R.drawable.ic_launcher_background.toString(),
                                                genreIds = listOf(
                                                    28,
                                                    12,
                                                    878
                                                ), // Example: Action, Adventure, Sci-Fi
                                                id = 12345,
                                                mediaType = "movie",
                                                originalLanguage = "en",
                                                originalTitle = "Sample Original Title",
                                                overview = "This is a sample overview of the movie. It describes the plot in a few sentences.",
                                                popularity = 123.45,
                                                posterPath = "/sample_poster_path.jpg",
                                                releaseDate = "2024-12-06",
                                                title = "Sample Movie Title",
                                                video = false,
                                                voteAverage = 8.5,
                                                voteCount = 1234
                                            )
                                        )
                                    }
                                }
                            )
                        )
                    ),
                    onTrendTvDateSelect = {},
                    trendTvOnClick = { _, _, _, _ -> },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@AnimatedContent
                )
            }
        }
    }
}

