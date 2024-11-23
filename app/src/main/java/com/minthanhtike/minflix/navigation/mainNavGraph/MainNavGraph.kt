package com.minthanhtike.minflix.navigation.mainNavGraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.minthanhtike.minflix.feature.detail.ui.DetailScn
import com.minthanhtike.minflix.feature.favourite.ui.FavScn
import com.minthanhtike.minflix.feature.home.ui.HomeScn
import com.minthanhtike.minflix.feature.search.ui.SearchScn
import com.minthanhtike.minflix.ui.component.LocalAnimatedContentScope
import com.minthanhtike.minflix.ui.component.LocalSharedTransitionScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavSetUp(
    padding: PaddingValues,
    navController: NavHostController
) {
    SharedTransitionLayout {
        CompositionLocalProvider(value = LocalSharedTransitionScope provides this) {
            NavHost(
                navController = navController,
                startDestination = HomeScreen,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec =
                        tween(
                            durationMillis = 2000,
                            easing = FastOutSlowInEasing
                        ),
                        initialOffset = { x -> -x }
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec =
                        tween(
                            durationMillis = 2000,
                            easing = FastOutSlowInEasing
                        ),
                        targetOffset = { x -> x }
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec =
                        tween(
                            durationMillis = 2000,
                            easing = FastOutSlowInEasing
                        ),
                        initialOffset = { x -> x }
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec =
                        tween(
                            durationMillis = 2000,
                            easing = FastOutSlowInEasing
                        ),
                        targetOffset = { x -> -x }
                    )
                }
            ) {
                composable<HomeScreen> {
                    CompositionLocalProvider(value = LocalAnimatedContentScope provides this) {
                        HomeScn(paddingValues = padding) { id: String, name: String ,image:String->
                            navController.navigate(
                                DetailScreen(
                                    name = name,
                                    id = id,
                                    image = image
                                )
                            )
                        }
                    }
                }

                composable<SearchScreen> {
                    SearchScn(paddingValues = padding)
                }

                composable<FavouriteScreen> {
                    FavScn(paddingValues = padding)
                }

                composable<DetailScreen> { navBackStack ->
                    CompositionLocalProvider(value = LocalAnimatedContentScope provides this) {
                        val id = navBackStack.toRoute<DetailScreen>().id
                        val name = navBackStack.toRoute<DetailScreen>().name
                        val image = navBackStack.toRoute<DetailScreen>().image
                        DetailScn(
                            id = id,
                            name = name,
                            image = image
                        )
                    }
                }
            }
        }
    }

}