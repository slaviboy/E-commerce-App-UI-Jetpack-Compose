package com.slaviboy.e_commerce_app_ui_compose

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.view.Display
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.window.layout.WindowMetricsCalculator
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.slaviboy.e_commerce_app_ui_compose.composable.*
import com.slaviboy.e_commerce_app_ui_compose.composable.destinations.DetailScreenDestination
import com.slaviboy.e_commerce_app_ui_compose.composable.destinations.HomeScreenDestination
import com.slaviboy.e_commerce_app_ui_compose.ui.theme.*
import com.slaviboy.e_commerce_app_ui_compose.viewmodels.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<ViewModel>()

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSize()
        hideSystemBars()

        setContent {

            /*
            // remove row, column overscroll effect
            CompositionLocalProvider(
                LocalOverScrollConfiguration provides null
            ) {}*/


            val expectedTop = with(LocalDensity.current) { 0.18.dh.toPx() }
            val expectedLeft = with(LocalDensity.current) { 0.24.dw.toPx() }
            val expectedWidth = with(LocalDensity.current) { 0.4.dh.toPx() }
            viewModel.setSharedImageToCoord(
                Rect(
                    expectedLeft,
                    expectedTop,
                    expectedLeft + expectedWidth,
                    expectedTop + expectedWidth
                )
            )

            val navHostEngine = rememberAnimatedNavHostEngine(
                navHostContentAlignment = Alignment.TopCenter,
                rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING
            )


            val scrollState = rememberScrollState()
            LaunchedEffect(viewModel.enableItemsScroll.value) {
                if (viewModel.enableItemsScroll.value) {
                    scrollState.enableScrolling(this)
                } else scrollState.disableScrolling(this)
            }

            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = rememberAnimatedNavController(),
                engine = navHostEngine
            ) {

                composable(HomeScreenDestination) {
                    HomeScreen(
                        navigator = destinationsNavigator,
                        viewModel = viewModel,
                        scrollState = scrollState
                    )
                }

                composable(DetailScreenDestination) {
                    DetailScreen(
                        navigator = destinationsNavigator,
                        viewModel = viewModel,
                        onHideSystemUI = {
                            hideSystemBars()
                        }
                    )
                }
            }

            SharedImage(viewModel)

            /*Box(
                modifier = Modifier
                    .height(1.dh.minus(10.dp))
                    .width(1.dw.minus(10.dp))
                    .background(Color(0x77ff00ff))
            )*/
        }
    }

    fun ScrollState.disableScrolling(scope: CoroutineScope) {
        scope.launch {
            scroll(scrollPriority = MutatePriority.PreventUserInput) {
                // Await indefinitely, blocking scrolls
                awaitCancellation()
            }
        }
    }

    fun ScrollState.enableScrolling(scope: CoroutineScope) {
        scope.launch {
            scroll(scrollPriority = MutatePriority.PreventUserInput) {
                // Do nothing, just cancel the previous indefinite "scroll"
            }
        }
    }


    private fun hideSystemBars() {

        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }


    val Context.navigationBarHeight: Int
        get() {
            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

            return if (Build.VERSION.SDK_INT >= 30) {
                windowManager
                    .currentWindowMetrics
                    .windowInsets
                    .getInsets(WindowInsets.Type.navigationBars())
                    .bottom

            } else {

                val currentDisplay = windowManager.defaultDisplay

                val appUsableSize = Point()
                val realScreenSize = Point()

                currentDisplay?.apply {
                    getSize(appUsableSize)
                    getRealSize(realScreenSize)
                }

                // navigation bar on the side
                if (appUsableSize.x < realScreenSize.x) {
                    return realScreenSize.x - appUsableSize.x
                }

                // navigation bar at the bottom
                return if (appUsableSize.y < realScreenSize.y) {
                    realScreenSize.y - appUsableSize.y
                } else 0
            }
        }

}

