package com.minthanhtike.minflix.feature.detail.ui

import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel

sealed interface HomeDetailUiState {
    data object Idle : HomeDetailUiState
    data object Loading : HomeDetailUiState
    data class Success(
        val homeDetailModel: HomeDetailModel
    ):HomeDetailUiState

    data class Error(
        val message: String
    ) : HomeDetailUiState
}

