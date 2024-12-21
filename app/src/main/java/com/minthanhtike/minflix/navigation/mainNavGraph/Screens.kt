package com.minthanhtike.minflix.navigation.mainNavGraph

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

object Screens {
    const val FavScn = "com.minthanhtike.minflix.navigation.mainNavGraph.FavouriteScreen"
    const val HomeScn = "com.minthanhtike.minflix.navigation.mainNavGraph.HomeScreen"
    const val DetailScn = "com.minthanhtike.minflix.navigation.mainNavGraph.DetailScreen"
    const val SearchScn = "com.minthanhtike.minflix.navigation.mainNavGraph.SearchScreen"
}

@Parcelize
@Serializable
sealed class AppScreens : Parcelable

@Parcelize
@Serializable
data object HomeScreen : AppScreens()

@Parcelize
@Serializable
data class DetailScreen(
    val id: Int,
    val name: String,
    val image: String,
    val type: String
) : AppScreens()

@Parcelize
@Serializable
data object SearchScreen : AppScreens()

@Parcelize
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

