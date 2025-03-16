package com.minthanhtike.minflix.feature.detail.ui

interface HomeDetailAction {
    data object Episodes : HomeDetailAction
    data object Trailers : HomeDetailAction
    data object About : HomeDetailAction
    data object Reviews : HomeDetailAction
    data object Recommendation: HomeDetailAction
}