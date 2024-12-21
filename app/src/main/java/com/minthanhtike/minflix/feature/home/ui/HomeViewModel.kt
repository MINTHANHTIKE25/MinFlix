package com.minthanhtike.minflix.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.minthanhtike.minflix.feature.home.domain.HomeUseCase
import com.minthanhtike.minflix.feature.home.domain.model.AiringTvTodayModel
import com.minthanhtike.minflix.feature.home.domain.model.NowPlayMovieModel
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import com.minthanhtike.minflix.feature.home.ui.component.HomeUiSize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _trendMovieState = MutableStateFlow<TrendingMovieState>(TrendingMovieState.Idle)
    private val trendMovieState = _trendMovieState.asStateFlow()

    private val _trendingTvState =
        MutableStateFlow<PagingData<TrendingTvModels>>(PagingData.empty())
    private val trendingTvState = _trendingTvState.asStateFlow()

    private val _nowPlayMoviesState =
        MutableStateFlow<PagingData<NowPlayMovieModel>>(PagingData.empty())
    private val nowPlayMovieState = _nowPlayMoviesState.asStateFlow()

    private val _getAirTvTodayState =
        MutableStateFlow<PagingData<AiringTvTodayModel>>(PagingData.empty())
    private val getAirTvTodayState = _getAirTvTodayState.asStateFlow()

    init {
        getTrendingMovie("day")
        getTrendingTv("day")
        getNowPlayMovie()
        getAiringTvToday()
        _uiState.update {
            it.copy(
                trendingMovieState = trendMovieState,
                trendingTvState = trendingTvState,
                nowPlayMoviesState = nowPlayMovieState,
                getAirTvTodayState = getAirTvTodayState
            )
        }
    }

    fun getTrendingMovie(time: String) {
        _trendMovieState.value = TrendingMovieState.Loading
        viewModelScope.launch {
            homeUseCase.getTrendingMovies(time)
                .fold(
                    onSuccess = { data ->
                        _trendMovieState.value = TrendingMovieState.Success(data)
                    },
                    onFailure = { error ->
                        _trendMovieState.value = TrendingMovieState.Error(
                            msg = "Sorry! We are in the maintenance of trending movies data ",
                            apiCall = { getTrendingMovie(it) }
                        )
                    }
                )
        }
    }

    fun getTrendingTv(time: String = "day") {
        viewModelScope.launch {
            homeUseCase.getTrendingTv(time)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { _trendingTvState.emit(it) }
        }
    }

    fun getNowPlayMovie() {
        viewModelScope.launch {
            homeUseCase.getNowPlayMovies()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { _nowPlayMoviesState.emit(it) }
        }
    }

    fun getAiringTvToday() {
        viewModelScope.launch {
            homeUseCase.getAiringTvToday()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { _getAirTvTodayState.emit(it) }
        }
    }

}

// Or the appropriate type for your unique identifier

fun <T> filterDuplicates(item: T): Boolean {
    val seenItems = mutableSetOf<T>()
    return if (seenItems.contains(item)) {
        false // Duplicate found, exclude the item
    } else {
        seenItems.add(item)
        true // New item, include it
    }
}