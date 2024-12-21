package com.minthanhtike.minflix.ui

import android.graphics.PorterDuff
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.navigation.mainNavGraph.AppScreens
import com.minthanhtike.minflix.navigation.mainNavGraph.FavouriteScreen
import com.minthanhtike.minflix.navigation.mainNavGraph.HomeScreen
import com.minthanhtike.minflix.navigation.mainNavGraph.MainNavSetUp
import com.minthanhtike.minflix.navigation.mainNavGraph.Screens
import com.minthanhtike.minflix.navigation.mainNavGraph.SearchScreen
import com.minthanhtike.minflix.ui.component.MainBottomNavBar
import com.minthanhtike.minflix.ui.component.MainTopAppBar
import com.minthanhtike.minflix.ui.component.bottomNavItems
import com.minthanhtike.minflix.ui.theme.MinFlixTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    var selectedNavItem by rememberSaveable {
        mutableStateOf<AppScreens>(HomeScreen)
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by rememberSaveable(navBackStackEntry?.destination?.route) {
        mutableStateOf(navBackStackEntry?.destination?.route)
    }
    val navSuiteType =
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = rememberTopAppBarState()
    )
    //tracking the backstack and selecting the bottom navigation items
    navController.addOnDestinationChangedListener(
        listener = { controller, destination, arguments ->
            when (destination.route) {
                Screens.HomeScn ->
                    selectedNavItem = HomeScreen

                Screens.SearchScn ->
                    selectedNavItem = SearchScreen

                Screens.FavScn ->
                    selectedNavItem = FavouriteScreen

            }
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color(0, 0, 0, 228),
        topBar = {
            AnimatedVisibility(
                visible = currentRoute.equals(Screens.HomeScn)
                        or currentRoute.equals(Screens.SearchScn)
                        or currentRoute.equals(Screens.FavScn),
                enter = expandIn(
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                ),
                exit = shrinkOut(
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            ) {
                MainTopAppBar(
                    topAppBarScrollBehavior = scrollBehavior,
                    modifier = Modifier.padding(
                        start = when (navSuiteType) {
                            NavigationSuiteType.NavigationRail -> 80.dp
                            NavigationSuiteType.NavigationDrawer -> 360.dp
                            else -> 0.dp
                        }
                    )
                )
            }
        },
        bottomBar = {
            if (navSuiteType == NavigationSuiteType.NavigationBar) {
                AnimatedVisibility(
                    visible = currentRoute.equals(Screens.HomeScn)
                            or currentRoute.equals(Screens.SearchScn)
                            or currentRoute.equals(Screens.FavScn),
                    enter = scaleIn(
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    ),
                    exit = scaleOut(
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    ),
                ) {
                    MainBottomNavBar(
                        navController = navController,
                        selectedNavItem = selectedNavItem
                    ) {
                        selectedNavItem = it
                    }
                }
            }
        }
    ) { contentPadding ->
        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                when (navSuiteType) {
                    NavigationSuiteType.NavigationRail -> {
                        if (
                            currentRoute.equals(Screens.HomeScn)
                            or currentRoute.equals(Screens.SearchScn)
                            or currentRoute.equals(Screens.FavScn)
                        ) {
                            NavigationRail(
                                containerColor = Color(0, 0, 0, 201)
                            ) {
                                Spacer(Modifier.weight(1f))
                                bottomNavItems.forEachIndexed { index, item ->
                                    NavigationRailItem(
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = item.icon),
                                                contentDescription = null
                                            )
                                        },
                                        colors = NavigationRailItemDefaults.colors(
                                            indicatorColor = colorResource(id = R.color.primary_color),
                                            unselectedTextColor = Color.White,
                                            selectedTextColor = Color.White,
                                            selectedIconColor = Color.White,
                                            unselectedIconColor = Color.White
                                        ),
                                        label = { Text(item.screenName) },
                                        selected = selectedNavItem == item.screen,
                                        onClick = {
                                            selectedNavItem = item.screen
                                            navController.navigate(item.screen) {
                                                // Avoid multiple copies of the same destination when
                                                // re-selecting the same item
                                                launchSingleTop = true
                                                // Restore state when re-selecting a previously selected item
                                                restoreState = true
                                                popUpTo(item.screen) {
                                                    inclusive = false
                                                }
                                            }
                                        }
                                    )
                                }
                                Spacer(Modifier.weight(1f))
                            }
                        }


                    }

                    NavigationSuiteType.NavigationBar -> {}

                    else -> {
                        NavigationSuite {
                            bottomNavItems.forEachIndexed { index, item ->
                                item(
                                    icon = {
                                        Icon(
                                            painterResource(id = item.icon),
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(item.screenName) },
                                    selected = selectedNavItem == item.screen,
                                    onClick = {
                                        selectedNavItem = item.screen
                                        navController.navigate(selectedNavItem)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        ) {
            Box {

                Image(
                    painter = painterResource(id = R.drawable.bg_img),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                MainNavSetUp(padding = contentPadding, navController,navSuiteType)
            }
        }

    }


}


@Preview
@Composable
private fun MainScnPrev() {
    MinFlixTheme {
        MainScreen()
    }
}