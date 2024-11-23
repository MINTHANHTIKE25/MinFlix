package com.minthanhtike.minflix.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.navigation.mainNavGraph.AppScreens
import com.minthanhtike.minflix.navigation.mainNavGraph.HomeScreen
import com.minthanhtike.minflix.navigation.mainNavGraph.MainNavSetUp
import com.minthanhtike.minflix.navigation.mainNavGraph.Screens
import com.minthanhtike.minflix.ui.component.MainBottomNavBar
import com.minthanhtike.minflix.ui.component.MainTopAppBar
import com.minthanhtike.minflix.ui.theme.MinFlixTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    var selectedNavItem by remember {
        mutableStateOf<AppScreens>(HomeScreen)
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    val imgList = buildList {
        repeat(20) {
            add(R.drawable.ic_launcher_background)
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            if (currentRoute.equals(Screens.HomeScn)
                or currentRoute.equals(Screens.SearchScn)
                or currentRoute.equals(Screens.FavScn)
            ) {
                MainBottomNavBar(
                    navController = navController,
                    onSelected = { selectedItem: AppScreens ->
                        selectedNavItem = selectedItem
                    }
                )
            }
        },

        topBar = {
            if (currentRoute.equals(Screens.HomeScn)
                or currentRoute.equals(Screens.SearchScn)
                or currentRoute.equals(Screens.FavScn)
            ) {
                MainTopAppBar(
                    topAppBarScrollBehavior = scrollBehavior
                )
            }
        },
    ) { contentPadding: PaddingValues ->
        Box {
            Image(
                painter = painterResource(id = R.drawable.bg_img),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .background(Color(0, 0, 0, 228))
                    .fillMaxSize()
            )
            MainNavSetUp(padding = contentPadding, navController)
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