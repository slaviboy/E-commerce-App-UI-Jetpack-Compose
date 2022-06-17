package com.slaviboy.e_commerce_app_ui_compose.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.slaviboy.e_commerce_app_ui_compose.Item
import com.slaviboy.e_commerce_app_ui_compose.R
import com.slaviboy.e_commerce_app_ui_compose.composable.destinations.DetailScreenDestination
import com.slaviboy.e_commerce_app_ui_compose.ui.theme.*
import com.slaviboy.e_commerce_app_ui_compose.viewmodels.ViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Destination(start = true, style = HomeScreenTransition::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: ViewModel,
    scrollState: ScrollState
) {

    val smallFont = 0.04.sh
    val bigFont = 0.085.sh

    LaunchedEffect(Unit) {
        scrollState.scrollTo(viewModel.itemsPosition.value)
    }

    val tabId = R.string.women
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {

        TopBar(Color(0xFF535353))
        Spacer(modifier = Modifier.height(0.01.dh))
        Text(
            text = stringResource(id = tabId),
            fontSize = bigFont,
            color = Color(0xFF575757),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(horizontal = 0.07.dw)
        )
        Spacer(modifier = Modifier.height(0.02.dh))

        val list = listOf(
            R.string.hand_bag,
            R.string.jewellery,
            R.string.footwear,
            R.string.dresses
        )
        var selectedItem by remember {
            mutableStateOf(0)
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(0.09.dw),
            modifier = Modifier
                .padding(horizontal = 0.07.dw)
        ) {

            items(list.size) { i ->
                Column() {
                    Text(
                        modifier = Modifier.clickable {
                            selectedItem = i
                        },
                        fontWeight = if (i == selectedItem) {
                            FontWeight.Bold
                        } else FontWeight.Normal,
                        text = stringResource(id = list[i]),
                        fontSize = smallFont,
                        color = Color(0xFF575757)
                    )

                    if (i == selectedItem) {
                        Spacer(modifier = Modifier.height(0.01.dh))
                        Box(
                            modifier = Modifier
                                .width(0.1.dw)
                                .height(2.dp)
                                .background(Color(0xFF575757))
                        )
                    }
                }
            }
        }

        val onClickListener: (layoutCoordinates: LayoutCoordinates, item: Item) -> Unit = { layoutCoordinatesChild, item ->

            viewModel.setItemsPosition(scrollState.value)

            val boundRect = layoutCoordinatesChild.boundsInWindow()
            val boundWithOffset = Rect(
                boundRect.left,
                if (boundRect.top < DeviceHeight / 2f) {
                    boundRect.top - (boundRect.width - boundRect.height)
                } else boundRect.top,
                boundRect.right,
                boundRect.bottom
            )
            viewModel.setEnableItemsScroll(false)
            viewModel.setSharedImageFromCoord(boundWithOffset)
            viewModel.setSharedImageResId(item.imageResId)
            viewModel.setChangeSharedImagePositionFrom(Screen.Home)
            viewModel.setInvisibleClickedImage(item)

            navigator.navigate(DetailScreenDestination())
        }

        val listItems = viewModel.items.value
        Spacer(modifier = Modifier.height(0.05.dh))
        Column(
            verticalArrangement = Arrangement.spacedBy(0.025.dh),
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(horizontal = 0.07.dw)
        ) {

            for (i in 0 until listItems.size / 2) {

                val item1 = listItems[i * 2]
                val item2 = listItems[i * 2 + 1]
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    //(item1 != viewModel.invisibleClickedImage.value)
                    Item(item1, viewModel, onClickListener)
                    Item(item2, viewModel, onClickListener)
                }
            }
        }

    }

    /* if (a.width != 0f) {
         Box(
             modifier = Modifier
                 .absoluteOffset { IntOffset(a.left.toInt(), a.top.toInt()) }
                 .width( with(LocalDensity.current) { a.width.toDp() } )
                 .height( with(LocalDensity.current) { a.height.toDp() })
                 .background(Color(0xDCFF00FF))
         )
     }*/

}

@Composable
fun Item(item: Item, viewModel: ViewModel, onClickListener: (layoutCoordinates: LayoutCoordinates, item: Item) -> Unit = { _, _ -> }) {

    val interactionSource = remember { MutableInteractionSource() }
    var layoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

    val smallFont = 0.04.sh
    Column(
        Modifier
            .width(0.41.dw)
            .height(0.6.dw)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                layoutCoordinates?.let {
                    onClickListener(it, item)
                }
            }
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(0.41.dw)
                .height(0.46.dw)
                .background(item.backgroundColor, RoundedCornerShape(0.05.dw))

        ) {

            Image(
                painterResource(id = item.imageResId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        layoutCoordinates = coordinates
                    },
                alpha = if (item == viewModel.invisibleClickedImage.value) viewModel.itemImageOpacity.value else 1f
            )
        }

        Spacer(modifier = Modifier.height(0.01.dw))
        Text(
            fontWeight = FontWeight.Normal,
            text = item.name,
            fontSize = smallFont,
            color = Color(0xFFCACACA)
        )
        Spacer(modifier = Modifier.height(0.01.dw))
        Text(
            fontWeight = FontWeight.Bold,
            text = "${item.currencySymbol}${item.price}",
            fontSize = smallFont.times(0.9f),
            color = Color(0xFF686868)
        )

    }
}

@Composable
fun TopBar(tintColor: Color, onBackButtonPressed: () -> Unit = {}, onSearchButtonPressed: () -> Unit = {}, onShoppingCardButtonPressed: () -> Unit = {}) {

    val interactionSource = remember { MutableInteractionSource() }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(0.06.dh)
            .offset(y = 0.01.dh)
    ) {

        Image(
            painterResource(id = R.drawable.ic_back),
            colorFilter = ColorFilter.tint(tintColor),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .padding(0.018.dh)
                .fillMaxHeight()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onBackButtonPressed.invoke()
                }
        )

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
        ) {

            Image(
                painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(tintColor),
                modifier = Modifier
                    .padding(0.015.dh)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onBackButtonPressed.invoke()
                    }
            )
            Image(
                painterResource(id = R.drawable.ic_cart),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(tintColor),
                modifier = Modifier
                    .padding(0.015.dh)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onBackButtonPressed.invoke()
                    }
            )
            Spacer(modifier = Modifier.width(0.02.dw))

        }
    }

}


