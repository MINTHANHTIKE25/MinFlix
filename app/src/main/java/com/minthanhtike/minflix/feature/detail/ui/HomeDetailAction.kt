package com.minthanhtike.minflix.feature.detail.ui

interface HomeDetailAction {
    data object Episodes : HomeDetailAction
    data object Cast : HomeDetailAction
    data object Reviews : HomeDetailAction
    data object Recommendation: HomeDetailAction
}