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
import com.minthanhtike.minflix.feature.detail.domain.model.MovieDetailModel
import com.minthanhtike.minflix.feature.favourite.ui.isSupportingPaneHidden
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
//    val homeImageState by detailVm.homeDetailImgState.collectAsStateWithLifecycle()

    val supportingPaneNav = rememberSupportingPaneScaffoldNavigator<Int>()
    val tabData = if (type.contains("Tv", true))
        if (supportingPaneNav.isSupportingPaneHidden)
            TabItems.entries else TabItems.entries.dropLast(1)
    else
        if (supportingPaneNav.isSupportingPaneHidden)
            TabItems.entries.drop(1) else TabItems.entries.dropLast(1)


    HomeDetailScnContent(
        id = id,
        name = name,
        image = image, type = type,
        animatedContentScope = localAnimatedContentScope,
        sharedTransitionScope = sharedTransitionScope,
        homeDetailUiState = homeDetailUiState,
        tabData = tabData,
        onTabSelect = { tabLabel ->
            if (type.contains("Tv", true)) {
                homeDetailVm.getTvOtherInfos(tabLabel)
            } else {
                homeDetailVm.getMovieOtherInfos(tabLabel)
            }
        },
        paneScaffoldNav = supportingPaneNav
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
    onTabSelect: (String) -> Unit,
    tabData: List<TabItems>,
    paneScaffoldNav: ThreePaneScaffoldNavigator<Int>
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
                        tabData = tabData
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
                    MovieDetailModel(
                        adult = false,
                        backdropPath = "backdrop/path",
                        budget = 1000000,
                        genres = listOf(MovieDetailModel.Genre(1, "Action")),
                        homepage = "https://example.com",
                        id = 1,
                        originCountry = listOf("US"),
                        originalLanguage = "en",
                        originalTitle = "Original Title",
                        overview = "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                                "mmmmmmmmmmmmmmmmmmmmmmmmasdfmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +
                                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmksdjhfasjasdfjhajshf" +
                                "asmdfaskdjfkahjfihaowshfjaskj;fkjasdlkjf;daskjf;kjasdflo;;fkjdsl;fkjsa;ol" +
                                "asdkfja;lskhjfds;okhfewadopifhjjasdoidsafl;kfdsjal;kfdasjd;lskja;ewkfjfdsja;dasfk" +
                                "as;ldkfj;oafjsdo;j;asdoijfsadoijfsd;oifjdaasd;ijaso;ijfd;oawssijasdo;hijjhasdoifj ",
                        popularity = 7.8,
                        posterPath = "poster/path",
                        releaseDate = "2024-12-06",
                        revenue = 5000000,
                        runtime = 120,
                        status = "Released",
                        tagline = "Tagline here",
                        title = "Movie Title",
                        video = false,
                        voteAverage = 8.5,
                        voteCount = 200,
                        productionCompany = "Example Productions",
                        movieImagesModel = emptyList()
                    )
                ),
                tabData = TabItems.entries,
                onTabSelect = {},
                paneScaffoldNav = rememberSupportingPaneScaffoldNavigator<Int>()
            )
        }
    }
}
