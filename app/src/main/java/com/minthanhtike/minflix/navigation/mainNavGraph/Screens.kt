package com.minthanhtike.minflix.navigation.mainNavGraph

import kotlinx.serialization.Serializable


@Serializable
data object Home

@Serializable
data class MovieDetail(
    val id: String
)

@Serializable
data object Movie

@Serializable
data object TVShow

@Serializable
data class TVShowDetail(
    val id: String
)

@Serializable
data object Setting

