package com.minthanhtike.minflix.navigation.mainNavGraph

import kotlinx.serialization.Serializable

object Screens{
    const val FavScn = "com.minthanhtike.minflix.navigation.mainNavGraph.FavouriteScreen"
    const val HomeScn = "com.minthanhtike.minflix.navigation.mainNavGraph.HomeScreen"
    const val DetailScn = "com.minthanhtike.minflix.navigation.mainNavGraph.DetailScreen"
    const val SearchScn = "com.minthanhtike.minflix.navigation.mainNavGraph.SearchScreen"
}

@Serializable
abstract class AppScreens

@Serializable
data object HomeScreen : AppScreens()

@Serializable
data class DetailScreen(
    val id: String,
    val name:String,
    val image:String
):AppScreens()

@Serializable
data object SearchScreen : AppScreens()

@Serializable
data object FavouriteScreen : AppScreens()

@Serializable
data object TVShow

@Serializable
data class TVShowDetail(
    val id: String
)

@Serializable
data object Setting

