package com.minthanhtike.minflix.feature.detail.ui.modelAndState

import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailCasterCrewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRecommendModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRelatedInfoModels
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailReviewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailTrailerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

sealed interface HomeDetailUiState {
    data object Idle : HomeDetailUiState
    data object Loading : HomeDetailUiState
    data class Success(
        val homeDetailModel: HomeDetailModel
    ) : HomeDetailUiState

    data class Error(
        val message: String
    ) : HomeDetailUiState
}

sealed interface HomeDetailCasterState {
    data object Idle : HomeDetailCasterState
    data object Loading : HomeDetailCasterState
    data class Success(
        val casterState: HomeDetailCasterCrewModel
    ) : HomeDetailCasterState

    data class Error(
        val message: Throwable
    ) : HomeDetailCasterState
}
sealed interface HomeDetailTrailerState {
    data object Idle : HomeDetailTrailerState
    data object Loading : HomeDetailTrailerState
    data class Success(
        val trailer: HomeDetailTrailerModel
    ) : HomeDetailTrailerState

    data class Error(
        val message: Throwable
    ) : HomeDetailTrailerState
}

data class HomeDetailRelatedInfos(
    var homeDetailRelatedInfos: Flow<PagingData<HomeDetailRelatedInfoModels>> = emptyFlow()
)

