package com.minthanhtike.minflix.feature.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.minthanhtike.minflix.feature.detail.domain.HomeDetailUseCase
import com.minthanhtike.minflix.navigation.mainNavGraph.DetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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


    init {
        if (type.contains("Tv", ignoreCase = true)) {
            getTvDetail()
        } else {
            getMovieDetail()
        }
    }

    fun onTabSelection(homeDetailAction: HomeDetailAction){
        when(homeDetailAction){
            is HomeDetailAction.Episodes -> {

            }
            is HomeDetailAction.Cast -> {

            }
            is HomeDetailAction.Reviews -> {

            }
            is HomeDetailAction.Recommendation -> {

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

    fun getTvOtherInfos(tabLabel:String){

    }

    fun getMovieOtherInfos(tabLabel: String){

    }
}