package com.slaviboy.e_commerce_app_ui_compose.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.slaviboy.e_commerce_app_ui_compose.MainActivity
import com.slaviboy.e_commerce_app_ui_compose.composable.destinations.HomeScreenDestination
import com.slaviboy.e_commerce_app_ui_compose.util.StaticMethods

@OptIn(ExperimentalAnimationApi::class)
object DetailScreenTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {

        return when (initialState.navDestination) {
            HomeScreenDestination ->
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(StaticMethods.NAVIGATION_TRANSITION_DURATION, StaticMethods.NAVIGATION_TRANSITION_DELAY)
                )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {

        return when (targetState.navDestination) {
            HomeScreenDestination ->
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(StaticMethods.NAVIGATION_TRANSITION_DURATION, StaticMethods.NAVIGATION_TRANSITION_DELAY)
                )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {

        return when (initialState.navDestination) {
            HomeScreenDestination ->
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(StaticMethods.NAVIGATION_TRANSITION_DURATION, StaticMethods.NAVIGATION_TRANSITION_DELAY)
                )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {

        return when (targetState.navDestination) {
            HomeScreenDestination ->
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(StaticMethods.NAVIGATION_TRANSITION_DURATION, StaticMethods.NAVIGATION_TRANSITION_DELAY)
                )
            else -> null
        }
    }
}