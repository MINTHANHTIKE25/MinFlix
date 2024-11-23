package com.minthanhtike.minflix.ui.component

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptions
import com.minthanhtike.minflix.R
import com.minthanhtike.minflix.navigation.mainNavGraph.AppScreens
import com.minthanhtike.minflix.navigation.mainNavGraph.DetailScreen
import com.minthanhtike.minflix.navigation.mainNavGraph.FavouriteScreen
import com.minthanhtike.minflix.navigation.mainNavGraph.HomeScreen
import com.minthanhtike.minflix.navigation.mainNavGraph.Screens
import com.minthanhtike.minflix.navigation.mainNavGraph.SearchScreen

val bottomNavItems = arrayOf(
    BottomNavItems(
        screen = SearchScreen,
        screenName = "Search",
        icon = R.drawable.search_ic
    ),
    BottomNavItems(
        screen = HomeScreen,
        screenName = "Home",
        icon = R.drawable.home_ic
    ),
    BottomNavItems(
        screen = FavouriteScreen,
        screenName = "Favourite",
        icon = R.drawable.fav_ic
    )
)

@Composable
fun MainBottomNavBar(
    navController: NavController,
    onSelected: (AppScreens) -> Unit
) {

    var selectedNavItem by remember { mutableStateOf<AppScreens>(HomeScreen) }

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

    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(75.dp)
            .background(
                color = Color(0, 0, 0, 201),
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 40.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            )
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            val animateIcSize by animateDpAsState(
                targetValue = if (item.screen == selectedNavItem) 36.dp else 30.dp,
                animationSpec = spring(),
                label = "Size",
            )
            val animateIcColor by animateColorAsState(
                targetValue = if (item.screen == selectedNavItem)
                    colorResource(id = R.color.primary_color) else Color.White,
                label = "Icon Color"
            )
            val animateDividerColor by animateColorAsState(
                targetValue = if (item.screen == selectedNavItem)
                    colorResource(id = R.color.primary_color) else Color.Transparent,
                label = "Divider Color"
            )
            val animateDividerWidth by animateDpAsState(
                targetValue = if (item.screen == selectedNavItem) 60.dp else 20.dp,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutLinearInEasing
                ),
                label = "Divider Width"
            )
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(
                        id = item.icon
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(animateIcSize)
                        .clickable {
                            selectedNavItem = item.screen
                            onSelected(selectedNavItem)
                            navController.navigate(item.screen) {
                                // Avoid multiple copies of the same destination when
                                // re-selecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                                popUpTo(item.screen){
                                    inclusive = false
                                }
                            }
                        },
                    tint = animateIcColor
                )
                HorizontalDivider(
                    color = animateDividerColor,
                    thickness = 5.dp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(animateDividerWidth)
                        .clip(
                            RoundedCornerShape(
                                topStart = 40.dp,
                                topEnd = 40.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp
                            )
                        )
                )
            }

        }

    }
}

data class BottomNavItems(
    val screen: AppScreens,
    val screenName: String,
    val icon: Int
)