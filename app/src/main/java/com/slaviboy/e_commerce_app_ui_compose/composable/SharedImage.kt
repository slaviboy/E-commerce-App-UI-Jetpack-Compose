package com.slaviboy.e_commerce_app_ui_compose.composable

import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.slaviboy.e_commerce_app_ui_compose.util.StaticMethods
import com.slaviboy.e_commerce_app_ui_compose.util.StaticMethods.NAVIGATION_TRANSITION_DELAY
import com.slaviboy.e_commerce_app_ui_compose.util.StaticMethods.NAVIGATION_TRANSITION_DURATION
import com.slaviboy.e_commerce_app_ui_compose.viewmodels.ViewModel


@Composable
fun SharedImage(viewModel: ViewModel) {

    // opacity animation for the shared image
    // if triggered from Home -> change the opacity of the shared image [0,1]
    // if triggered from Detail -> change the opacity of the shared image [1,0]
    LaunchedEffect(viewModel.changeSharedImagePositionFrom.value) {

        val duration: Int
        val delay: Int
        val opacityFrom: Float
        val opacityTo: Float

        if (viewModel.changeSharedImagePositionFrom.value is Screen.Home) {
            duration = NAVIGATION_TRANSITION_DELAY
            delay = 0
            opacityFrom = 0f
            opacityTo = 1f
        } else {
            duration = NAVIGATION_TRANSITION_DELAY
            delay = NAVIGATION_TRANSITION_DURATION + NAVIGATION_TRANSITION_DELAY
            opacityFrom = 1f
            opacityTo = 0f
        }

        val animation = TargetBasedAnimation(
            animationSpec = tween(duration, delay),
            typeConverter = Float.VectorConverter,
            initialValue = opacityFrom,
            targetValue = opacityTo
        )

        var playTime = 0L
        val startTime = withFrameNanos { it }
        do {
            playTime = withFrameNanos { it } - startTime
            val animationValue = animation.getValueFromNanos(playTime)
            viewModel.setSharedImageOpacity(animationValue)

        } while (playTime <= animation.durationNanos)

        // on last frame set item opacity to 0
        if (viewModel.changeSharedImagePositionFrom.value is Screen.Home) {
            viewModel.setItemImageOpacity(0f)
        }
    }

    var left by remember { mutableStateOf(0f) }
    var top by remember { mutableStateOf(0f) }
    var width by remember { mutableStateOf(0f) }


    // transition animation for the shared image
    // it changes the position and size of the shared image
    LaunchedEffect(viewModel.changeSharedImagePositionFrom.value) {

        val animation = TargetBasedAnimation(
            animationSpec = tween(NAVIGATION_TRANSITION_DURATION, NAVIGATION_TRANSITION_DELAY),
            typeConverter = Float.VectorConverter,
            initialValue = 0f,
            targetValue = 1f
        )

        val from = if (viewModel.changeSharedImagePositionFrom.value is Screen.Home) {
            viewModel.sharedImageFromCoord.value
        } else viewModel.sharedImageToCoord.value

        val to = if (viewModel.changeSharedImagePositionFrom.value is Screen.Home) {
            viewModel.sharedImageToCoord.value
        } else viewModel.sharedImageFromCoord.value

        // offset and size for changing the shared image position and size
        val fromLeft = from.left
        val fromTop = from.top
        val fromSize = from.width
        val toLeft = to.left
        val toTop = to.top
        val toSize = to.width

        var playTime = 0L
        val startTime = withFrameNanos { it }
        do {
            playTime = withFrameNanos { it } - startTime
            val animationValue = animation.getValueFromNanos(playTime)
            left = fromLeft + animationValue * (toLeft - fromLeft)
            top = fromTop + animationValue * (toTop - fromTop)
            width = fromSize + animationValue * (toSize - fromSize)

        } while (playTime <= animation.durationNanos)

        // on last frame set item opacity to 1
        if (viewModel.changeSharedImagePositionFrom.value is Screen.Detail) {
            viewModel.setItemImageOpacity(1f)
            viewModel.setEnableItemsScroll(true)
        }
    }

    Image(
        painterResource(id = viewModel.setSharedImageResId.value),
        contentDescription = null,
        modifier = Modifier
            .absoluteOffset { IntOffset(left.toInt(), top.toInt()) }
            .width(with(LocalDensity.current) { width.toDp() })
            .height(with(LocalDensity.current) { width.toDp() }),
        alpha = viewModel.sharedImageOpacity.value
    )

}


