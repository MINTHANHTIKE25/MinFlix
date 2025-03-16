package com.minthanhtike.minflix.feature.favourite.ui

import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.minthanhtike.minflix.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlin.properties.Delegates


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
val ThreePaneScaffoldNavigator<*>.isSupportingPaneHidden: Boolean
    get() = scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun FavScn(
    modifier: Modifier = Modifier, paddingValues: PaddingValues,
    navigator: ThreePaneScaffoldNavigator<Any> = rememberSupportingPaneScaffoldNavigator<Any>()
) {
    val context = LocalContext.current as ComponentActivity
    var isFullscreen by remember { mutableStateOf(false) }

    val youTubePlayerView = remember { YouTubePlayerView(context) }
    val fullscreenViewContainer = remember { FrameLayout(context) }

//    AndroidView(
//        factory = {
//            WebView(context).apply {
//                settings.javaScriptEnabled = true
//                webViewClient = WebViewClient()
//                webChromeClient = WebChromeClient()
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//            }
//        },
//        update = {
//            it.loadUrl("https://www.themoviedb.org/video/play?key=YvGSK8mIlt8")
//        },
//        modifier = Modifier.padding(paddingValues)
//    )


//    DisposableEffect(Unit) {
//        val iFramePlayerOptions = IFramePlayerOptions.Builder()
//            .controls(1)
//            .fullscreen(1)
//            .build()
//
//        // Manual initialization with options
//        youTubePlayerView.enableAutomaticInitialization = false
//
//        // Fullscreen listener
//        youTubePlayerView.addFullscreenListener(object : FullscreenListener {
//            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
//                isFullscreen = true
//                youTubePlayerView.visibility = View.GONE
//                fullscreenViewContainer.visibility = View.VISIBLE
//                fullscreenViewContainer.addView(fullscreenView)
////                context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//            }
//
//            override fun onExitFullscreen() {
//                isFullscreen = false
//                youTubePlayerView.visibility = View.VISIBLE
//                fullscreenViewContainer.visibility = View.GONE
//                fullscreenViewContainer.removeAllViews()
////                context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            }
//        })
//
//        // Initialize YouTubePlayerView
//        youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                youTubePlayer.loadVideo("S0Q4gqBUs7c", 0f)
//                if (isFullscreen){
//                    youTubePlayer.toggleFullscreen()
//                }
//            }
//        }, iFramePlayerOptions)
//
//        // Add YouTubePlayerView as a lifecycle observer
//        context.lifecycle.addObserver(youTubePlayerView)
//
//        onDispose {
//            context.lifecycle.removeObserver(youTubePlayerView)
//            youTubePlayerView.release()
//        }
//    }

    BackHandler(enabled = isFullscreen) {
        if (isFullscreen) {
            isFullscreen = false
        } else {
            context.finish()
        }
    }

//    Box(
//        modifier = Modifier
//            .padding(paddingValues)
//            .fillMaxSize()
//            .background(Color.Gray)
//    ) {
//        AndroidView(
//            factory = { youTubePlayerView },
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(16f / 9f)
//        )
//
//        if (isFullscreen) {
//            AndroidView(
//                factory = { fullscreenViewContainer },
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }


//    SupportingPaneScaffold(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues),
//        directive = navigator.scaffoldDirective,
//        value = navigator.scaffoldValue,
//        mainPane = {
//            val result = navigator.currentDestination?.content?.toString()
//            AnimatedPane(
//                modifier = Modifier
//                    .safeDrawingPadding()
//            ) {
//                MainPaneView(
//                    resultFromSupportingPane = result,
//                ) {
//                    if (!navigator.isSupportingPaneHidden) {
//                        navigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
//                    }
//                }
//            }
//
//        },
//        supportingPane = {
//            AnimatedPane(
//                modifier = Modifier
//                    .safeContentPadding()
//            ) {
//                SupportingPaneView(
//                ) {
//                    navigator.navigateTo(
//                        SupportingPaneScaffoldRole.Main,
//                        content = it
//                    )
//                }
//            }
//        }
//    )
}


@Composable
fun MainPaneView(
    modifier: Modifier = Modifier,
    resultFromSupportingPane: String?,
    onClick: () -> Unit,
) {


//    Box(
//        modifier = if (isFullScn) {
//            Modifier
//                .background(Color.Black)
//                .fillMaxSize()
//        } else {
//            Modifier
//        }
//    ){
//    Column {
//    AndroidView(
//        factory = {
//            val youTubePlayerView = YouTubePlayerView(it)
//            val frameLayout = FrameLayout(it)
//            val button = Button(it)
//            frameLayout.layoutParams = FrameLayout.LayoutParams(1000,1000)
//            // we need to initialize manually in order to pass IFramePlayerOptions to the player
//            youTubePlayerView.enableAutomaticInitialization = false
//
//            youTubePlayerView.addFullscreenListener(
//                object : FullscreenListener {
//                    override fun onEnterFullscreen(
//                        fullscreenView: View,
//                        exitFullscreen: () -> Unit
//                    ) {
//                        isFullScn = true
//                        // the video will continue playing in fullscreenView
//                        youTubePlayerView.visibility = View.GONE
//                        frameLayout.visibility = View.VISIBLE
//                        frameLayout.addView(fullscreenView)
//
//                    }
//
//                    override fun onExitFullscreen() {
//                        isFullScn= false
//
//                        // the video will continue playing in the player
//                        youTubePlayerView.visibility = View.VISIBLE
//                        frameLayout.visibility = View.GONE
//                        frameLayout.removeAllViews()
//                    }
//
//                }
//            )
//
//            youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    youTubePlayer.loadOrCueVideo(lifecycle.lifecycle, "S0Q4gqBUs7c", 0f)
//                    button.setOnClickListener {
//                        youTubePlayer.toggleFullscreen()
//                    }
//                }
//            }, iFramePlayerOptions)
//            lifecycle.lifecycle.addObserver(youTubePlayerView)
//            youTubePlayerView
//        },
//        modifier = Modifier.fillMaxSize()
//    )
//    }
//    }


//    Box(
//        modifier = modifier
//    ) {
//        Column(
//            modifier = Modifier
//                .align(Alignment.Center)
//                .clickable {
//                    onClick()
//                }
//        ) {
//            repeat(3) {
//                Text(
//                    text = "Go To Main Pane $it",
//                    color = Color.Blue,
//                )
//                resultFromSupportingPane?.let { str ->
//                    Text(
//                        text = str,
//                        color = Color.Blue
//                    )
//                }
//            }
//        }
//    }
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

