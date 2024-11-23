package com.minthanhtike.minflix.feature.home.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.paging.PagingData
import com.minthanhtike.minflix.feature.home.data.repo.HomeRepo
import com.minthanhtike.minflix.feature.home.domain.model.AiringTvTodayModel
import com.minthanhtike.minflix.feature.home.domain.model.NowPlayMovieModel
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val trendingMovieState: Flow<TrendingMovieState> = emptyFlow(),
    val trendingTvState:Flow<PagingData<TrendingTvModels>> = emptyFlow(),
    val nowPlayMoviesState:Flow<PagingData<NowPlayMovieModel>> = emptyFlow(),
    val getAirTvTodayState:Flow<PagingData<AiringTvTodayModel>> = emptyFlow()
)

sealed interface TrendingMovieState {
    data object Idle : TrendingMovieState
    data object Loading : TrendingMovieState
    data class Success(
        val trendMovie: List<TrendingMovieModels>
    ) : TrendingMovieState
    data class Error(
        val msg: String
    ) : TrendingMovieState

    data object ErrorShown : TrendingMovieState
}