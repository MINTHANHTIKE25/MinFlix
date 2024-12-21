package com.minthanhtike.minflix.navigation.mainNavGraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.minthanhtike.minflix.feature.detail.ui.HomeDetailScn
import com.minthanhtike.minflix.feature.favourite.ui.FavScn
import com.minthanhtike.minflix.feature.home.ui.HomeScn
import com.minthanhtike.minflix.feature.search.ui.SearchScn
import com.minthanhtike.minflix.ui.component.LocalAnimatedContentScope
import com.minthanhtike.minflix.ui.component.LocalSharedTransitionScope
import kotlin.math.exp

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainNavSetUp(
    padding: PaddingValues,
    navController: NavHostController,
    navSuitType: NavigationSuiteType
) {
    SharedTransitionLayout {
        CompositionLocalProvider(value = LocalSharedTransitionScope provides this) {
            NavHost(
                navController = navController,
                startDestination = HomeScreen,
                enterTransition = {
                    when (navSuitType) {
                        NavigationSuiteType.NavigationBar -> {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec =
                                tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                ),
                                initialOffset = { x -> -x }
                            )
                        }

                        else -> {
                            slideInHorizontally(
                                initialOffsetX = { x -> x},
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                },
                exitTransition = {
                    when (navSuitType) {
                        NavigationSuiteType.NavigationBar -> {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec =
                                tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                ),
                                targetOffset = { x -> x }
                            )
                        }

                        else -> {
                            slideOutHorizontally(
                                targetOffsetX = {x -> x},
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                },
                popEnterTransition = {
                    when (navSuitType) {
                        NavigationSuiteType.NavigationBar -> {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec =
                                tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                ),
                                initialOffset = { x -> x }
                            )
                        }

                        else -> {
                            slideInHorizontally(
                                initialOffsetX = { x -> x},
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                },
                popExitTransition = {
                    when (navSuitType) {
                        NavigationSuiteType.NavigationBar -> {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec =
                                tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                ),
                                targetOffset = { x -> x }
                            )
                        }

                        else -> {
                            slideOutHorizontally(
                                targetOffsetX = {x -> x},
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                }
            ) {
                composable<HomeScreen> {
                    CompositionLocalProvider(value = LocalAnimatedContentScope provides this) {
                        HomeScn(paddingValues = padding) { id: Int, name: String, image: String, type: String ->
                            navController.navigate(
                                DetailScreen(
                                    name = name,
                                    id = id,
                                    image = image,
                                    type = type
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
                        val type = navBackStack.toRoute<DetailScreen>().type
                        HomeDetailScn(
                            id = id,
                            name = name,
                            image = image,
                            type = type
                        )
                    }
                }
            }
        }
    }

}