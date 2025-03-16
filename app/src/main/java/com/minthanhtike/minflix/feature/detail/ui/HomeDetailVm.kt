package com.minthanhtike.minflix.feature.detail.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.minthanhtike.minflix.feature.detail.domain.HomeDetailUseCase
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailCasterCrewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRecommendModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRelatedInfoModels
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailReviewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailTrailerModel
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailCasterState
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailRelatedInfos
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailTrailerState
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailUiState
import com.minthanhtike.minflix.navigation.mainNavGraph.DetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDetailVm @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val homeDetailUseCase: HomeDetailUseCase,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<DetailScreen>().id
    private val type = savedStateHandle.toRoute<DetailScreen>().type

    private val _homeDetailUiState = MutableStateFlow<HomeDetailUiState>(HomeDetailUiState.Idle)
    val homeDetailUiState: StateFlow<HomeDetailUiState> = _homeDetailUiState.asStateFlow()

    private var casterCrewState =
        MutableStateFlow<HomeDetailCasterState>(HomeDetailCasterState.Idle)

    private var reviewState =
        MutableStateFlow(PagingData.empty<HomeDetailReviewModel>())

    private var recommendState =
        MutableStateFlow(PagingData.empty<HomeDetailRecommendModel>())

    private var trailerState =
        MutableStateFlow<HomeDetailTrailerState>(HomeDetailTrailerState.Idle)

    private var _homeDetailRelatedInfoState = MutableStateFlow(HomeDetailRelatedInfos())
    val homeDetailRelatedInfos = _homeDetailRelatedInfoState.asStateFlow()

    init {
        if (type.contains("Tv", ignoreCase = true)) {
            getTvDetail()
            onTabSelection(HomeDetailAction.Episodes)
        } else {
            getMovieDetail()
            getMovieCaster()
            getMovieRecommendation()
            getMovieReviews()
            getTrailer()
            onTabSelection(HomeDetailAction.About)
        }
    }

    fun onTabSelection(homeDetailAction: HomeDetailAction) {
        when (homeDetailAction) {
            is HomeDetailAction.Episodes -> {}

            is HomeDetailAction.About -> {
                viewModelScope.launch {
                    casterCrewState.collectLatest {
                        Log.wtf("casterCrewState", "${casterCrewState.value}")
                        val result = when (val data = it) {

                            is HomeDetailCasterState.Loading -> flowOf(
                                PagingData.from(
                                    data = emptyList<HomeDetailCasterCrewModel>(),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.Loading,
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )

                            is HomeDetailCasterState.Error -> flowOf(
                                PagingData.from(
                                    data = emptyList<HomeDetailCasterCrewModel>(),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.Error(data.message),
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )

                            is HomeDetailCasterState.Success -> flowOf(
                                PagingData.from(
                                    data = listOf(data.casterState),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.NotLoading(false),
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )

                            HomeDetailCasterState.Idle -> flowOf(
                                PagingData.from(
                                    data = emptyList<HomeDetailCasterCrewModel>(),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.NotLoading(false),
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )
                        }
                        _homeDetailRelatedInfoState.update {
                            it.copy(
                                homeDetailRelatedInfos = result.map { data -> data.map { res -> res } }
                            )
                        }
                    }
                }
            }

            is HomeDetailAction.Reviews -> {
                _homeDetailRelatedInfoState.update {
                    it.copy(
                        homeDetailRelatedInfos = reviewState.map { data ->
                            data.map { item -> item as HomeDetailRelatedInfoModels}
                        }
                    )
                }
            }

            is HomeDetailAction.Trailers -> {
                viewModelScope.launch {
                    trailerState.collectLatest {
                        val result = when (val data = it) {
                            is HomeDetailTrailerState.Loading -> flowOf(
                                PagingData.from(
                                    data = emptyList<HomeDetailTrailerModel>(),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.Loading,
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )

                            is HomeDetailTrailerState.Error -> flowOf(
                                PagingData.from(
                                    data = emptyList<HomeDetailTrailerModel>(),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.Error(data.message),
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )

                            is HomeDetailTrailerState.Success -> flowOf(
                                PagingData.from(
                                    data = listOf(data.trailer),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.NotLoading(false),
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )

                            HomeDetailTrailerState.Idle -> flowOf(
                                PagingData.from(
                                    data = emptyList<HomeDetailTrailerModel>(),
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.NotLoading(false),
                                        append = LoadState.NotLoading(false),
                                        prepend = LoadState.NotLoading(false),
                                    ),
                                )
                            )
                        }

                        _homeDetailRelatedInfoState.update {
                            it.copy(
                                homeDetailRelatedInfos = result.map { data -> data.map { res -> res } }
                            )
                        }
                    }
                }

            }

            is HomeDetailAction.Recommendation -> {
                _homeDetailRelatedInfoState.update {
                    it.copy(
                        homeDetailRelatedInfos = recommendState.map { data ->
                            data.map { item -> item }
                        }
                    )
                }
            }
        }
    }

    private fun getTvDetail() {
        _homeDetailUiState.value = HomeDetailUiState.Loading
        viewModelScope.launch {
            homeDetailUseCase.getTvDetail(id)
                .fold(
                    onSuccess = {
                        _homeDetailUiState.value = HomeDetailUiState.Success(it)
                    },
                    onFailure = {
                        _homeDetailUiState.value = HomeDetailUiState.Error(it.message ?: "Oops!")
                    }
                )
        }
    }

    private fun getMovieDetail() {
        _homeDetailUiState.value = HomeDetailUiState.Loading
        viewModelScope.launch {
            homeDetailUseCase.getMovieDetail(id)
                .fold(
                    onSuccess = {
                        _homeDetailUiState.value = HomeDetailUiState.Success(it)
                    },
                    onFailure = {
                        _homeDetailUiState.value =
                            HomeDetailUiState.Error(it.message ?: "An Unknown Error Occurs")
                    }
                )
        }
    }

    private fun getMovieRecommendation() {
        viewModelScope.launch {
            homeDetailUseCase.getMovieRecommend(id)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    recommendState.emit(it)
                }
        }
    }

    private fun getMovieReviews() {
        viewModelScope.launch {
            homeDetailUseCase.getMovieReview(id)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { reviewState.emit(it) }
        }
    }

    private fun getMovieCaster() {
        casterCrewState.value = HomeDetailCasterState.Loading
        viewModelScope.launch {
            homeDetailUseCase.getMovieCasts(id)
                .fold(
                    onSuccess = {
                        casterCrewState.value = HomeDetailCasterState.Success(it)
                    },
                    onFailure = {
                        casterCrewState.value = HomeDetailCasterState.Error(it)
                    }
                )
        }
    }

    private fun getTrailer(seasonNo: Int = 1) {
        trailerState.value = HomeDetailTrailerState.Loading
        viewModelScope.launch {
            homeDetailUseCase.getTrailers(id, seasonNo, type)
                .fold(
                    onSuccess = {
                        trailerState.value = HomeDetailTrailerState.Success(it)
                    },
                    onFailure = {
                        trailerState.value = HomeDetailTrailerState.Error(it)
                    }
                )
        }
    }
}