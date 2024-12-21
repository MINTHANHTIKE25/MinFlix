package com.minthanhtike.minflix.feature.favourite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val ThreePaneScaffoldNavigator<*>.isSupportingPaneHidden: Boolean
    get() = scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun FavScn(
    modifier: Modifier = Modifier, paddingValues: PaddingValues,
    navigator: ThreePaneScaffoldNavigator<Any> = rememberSupportingPaneScaffoldNavigator<Any>()
) {
    SupportingPaneScaffold(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            val result = navigator.currentDestination?.content?.toString()
            AnimatedPane(
                modifier = Modifier
                    .safeDrawingPadding()
            ) {
                MainPaneView(
                    resultFromSupportingPane = result,
                ) {
                    if (!navigator.isSupportingPaneHidden) {
                        navigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                    }
                }
            }

        },
        supportingPane = {
            AnimatedPane(
                modifier = Modifier
                    .safeContentPadding()
            ) {
                SupportingPaneView(
                ) {
                    navigator.navigateTo(
                        SupportingPaneScaffoldRole.Main,
                        content = it
                    )
                }
            }
        }
    )
}


@Composable
fun MainPaneView(
    modifier: Modifier = Modifier,
    resultFromSupportingPane: String?,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    onClick()
                }
        ) {
            repeat(3) {
                Text(
                    text = "Go To Main Pane $it",
                    color = Color.Blue,
                )
                resultFromSupportingPane?.let { str ->
                    Text(
                        text = str,
                        color = Color.Blue
                    )
                }
            }
        }
    }
}

@Composable
fun SupportingPaneView(modifier: Modifier = Modifier, onClick: (str: String) -> Unit) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            repeat(3) {
                Text(
                    text = "Go To Supporting Pane $it times",
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable {
                            onClick("$it times")
                        }
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview(showBackground = true)
@Composable
private fun FavScnPrev() {
    FavScn(
        paddingValues = PaddingValues(),
        navigator = rememberSupportingPaneScaffoldNavigator<Any>()
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview(device = "spec:id=reference_tablet,shape=Normal,width=1280,height=800,unit=dp,dpi=240")
@Composable
private fun FavScnTablet() {
    FavScn(paddingValues = PaddingValues())
}

