package com.minthanhtike.minflix.feature.detail.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minthanhtike.minflix.common.TabItems
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailRelatedInfos
import com.minthanhtike.minflix.feature.detail.ui.modelAndState.HomeDetailUiState
import com.minthanhtike.minflix.ui.component.LocalAnimatedContentScope
import com.minthanhtike.minflix.ui.component.LocalSharedTransitionScope


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeDetailScn(
    modifier: Modifier = Modifier,
    homeDetailVm: HomeDetailVm = hiltViewModel(),
    id: Int, name: String, image: String, type: String
) {
    val localAnimatedContentScope = LocalAnimatedContentScope.current
    val sharedTransitionScope = LocalSharedTransitionScope.current

    val homeDetailUiState by homeDetailVm.homeDetailUiState.collectAsStateWithLifecycle()
    val homeDetailRelatedInfos by homeDetailVm.homeDetailRelatedInfos.collectAsStateWithLifecycle()

    val supportingPaneNav = rememberSupportingPaneScaffoldNavigator<Int>()
    val tabData = if (type.contains("Tv", true))
        TabItems.entries
    else
        TabItems.entries.drop(1)


    HomeDetailScnContent(
        id = id,
        name = name,
        image = image, type = type,
        animatedContentScope = localAnimatedContentScope,
        sharedTransitionScope = sharedTransitionScope,
        homeDetailUiState = homeDetailUiState,
        tabData = tabData,
        onTabSelect = { tabAction ->
            homeDetailVm.onTabSelection(tabAction)
        },
        paneScaffoldNav = supportingPaneNav,
        homeDetailRelatedInfos = homeDetailRelatedInfos
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeDetailScnContent(
    modifier: Modifier = Modifier,
    id: Int, name: String,
    image: String, type: String,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    homeDetailUiState: HomeDetailUiState,
    onTabSelect: (HomeDetailAction) -> Unit,
    tabData: List<TabItems>,
    paneScaffoldNav: ThreePaneScaffoldNavigator<Int>,
    homeDetailRelatedInfos: HomeDetailRelatedInfos
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
        contentColor = Color.Black
    ) {
        SupportingPaneScaffold(
            modifier = Modifier
                .fillMaxSize(),
            directive = paneScaffoldNav.scaffoldDirective,
            value = paneScaffoldNav.scaffoldValue,
            mainPane = {
                AnimatedPane(
                    modifier = Modifier.systemBarsPadding()
                ) {
                    HomeDetailMainPane(
                        id = id,
                        name = name,
                        image = image,
                        type = type,
                        animatedContentScope = animatedContentScope,
                        sharedTransitionScope = sharedTransitionScope,
                        homeDetailUiState = homeDetailUiState,
                        onTabSelect = onTabSelect,
                        tabData = tabData,
                        homeDetailRelatedInfos = homeDetailRelatedInfos
                    )
                }
            },
            supportingPane = {
                AnimatedPane(
                    modifier = Modifier.systemBarsPadding()
                ) {
                    HomeDetailSupportingPane()
                }

            }
        )
    }


}


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Preview(device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420")
@Preview(device = "spec:id=reference_tablet,shape=Normal,width=1280,height=800,unit=dp,dpi=240")
@Composable
private fun DetailScnPrev() {
    SharedTransitionLayout {
        AnimatedContent(targetState = false, label = "") { s ->
            s
            HomeDetailScnContent(
                id = 0,
                name = "min than htike",
                image = "", type = "",
                animatedContentScope = this,
                sharedTransitionScope = this@SharedTransitionLayout,
                homeDetailUiState = HomeDetailUiState.Success(
                    HomeDetailModel(
                        title = "", rating = 0.0,
                        companyName = "", backdropImg = "", overView = "",
                        imagesList = emptyList(),
                        releaseDate = "12-3-2001",
                        spokenLang = listOf("English"),
                        homePage = "https://www.home.com",
                        productionCompany = listOf("Universal Pictures", "Sony Pictures"),
                        budget = "3Millions"
                    )
                ),
                tabData = TabItems.entries,
                onTabSelect = {},
                paneScaffoldNav = rememberSupportingPaneScaffoldNavigator<Int>(),
                homeDetailRelatedInfos = HomeDetailRelatedInfos()
            )
        }
    }
}
