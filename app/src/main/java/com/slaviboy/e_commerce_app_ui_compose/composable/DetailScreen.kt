package com.slaviboy.e_commerce_app_ui_compose.composable

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.slaviboy.e_commerce_app_ui_compose.R
import com.slaviboy.e_commerce_app_ui_compose.composable.destinations.HomeScreenDestination
import com.slaviboy.e_commerce_app_ui_compose.ui.theme.dh
import com.slaviboy.e_commerce_app_ui_compose.ui.theme.dw
import com.slaviboy.e_commerce_app_ui_compose.ui.theme.sh
import com.slaviboy.e_commerce_app_ui_compose.ui.theme.sw
import com.slaviboy.e_commerce_app_ui_compose.viewmodels.ViewModel

fun Rect.clone(): Rect {
    return Rect(this.left, this.top, this.right, this.bottom)
}

@OptIn(ExperimentalAnimationApi::class)
@Destination(style = DetailScreenTransitions::class)
@Composable
fun DetailScreen(
    navigator: DestinationsNavigator,
    viewModel: ViewModel,
    onHideSystemUI: () -> Unit
) {

    val smallFont = 0.04.sh
    val bigFont = 0.085.sh
    val interactionSource = remember { MutableInteractionSource() }

    Background()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onHideSystemUI.invoke()
            }
    ) {

        TopBar(tintColor = Color.White,
            onBackButtonPressed = {
                viewModel.setChangeSharedImagePositionFrom(Screen.Detail)
                navigator.navigate(HomeScreenDestination())
            }
        )

        Text(
            text = viewModel.itemDescription.value,
            fontSize = smallFont,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(start = 0.07.dw)
                .offset(y = 0.09.dh)
        )
        Text(
            text = viewModel.itemName.value,
            fontSize = bigFont,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 0.07.dw)
                .offset(y = 0.115.dh)
        )


        Text(
            text = stringResource(id = R.string.price),
            fontSize = smallFont,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(start = 0.07.dw)
                .offset(y = 0.33.dh)
        )
        Text(
            text = viewModel.getItemPrice(),
            fontSize = bigFont,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 0.07.dw)
                .offset(y = 0.35.dh)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 0.1.dh)
                .padding(horizontal = 0.07.dw)
                .offset(y = 0.45.dh)
        ) {

            val items2 = listOf(Color(0xFF356C95), Color(0xFFF8C078), Color(0xFFA29B9B))
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = stringResource(id = R.string.color),
                    color = Color(0xFF7D7D7D),
                    fontSize = smallFont,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(0.01.dh))
                LazyRow() {

                    items(items2.size) { i ->
                        val color = items2[i]
                        Box(
                            modifier = Modifier
                                .width(0.03.dh)
                                .height(0.03.dh)
                                .border(BorderStroke(2.dp, color.copy(alpha = 0.2f)), shape = CircleShape)
                                .padding(0.004.dh)
                                .background(color, shape = CircleShape)
                        ) {}
                        Spacer(modifier = Modifier.width(0.02.dw))
                    }
                }
            }

            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = stringResource(id = R.string.size),
                    color = Color(0xFF7D7D7D),
                    fontSize = smallFont,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(0.005.dh))
                Text(
                    text = "12 cm",
                    color = Color(0xFF575757),
                    fontSize = smallFont.times(1.3f),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        val scroll = rememberScrollState(0)
        Box(
            modifier = Modifier
                .height(0.15.dh)
                .fillMaxWidth()
                .offset(y = 0.66.dh)
                .padding(horizontal = 0.07.dw)
        ) {

            Text(
                text = "A bag is a kind of soft container. It can hold or carry things. It may be made from cloth, leather, plastic, or paper. Many bags are disposable but some are made to use for a long time. A bag may have one or two handles or a shoulder strap. Bags come in different shapes and sizes depending on how they will be used. A small bag that can be carried with a single hand is sometimes called a handbag, purse, or pocketbook. Children can suffocate, or stop breathing and die, when they put certain kinds of bags over their heads, due to having the supply of oxygen cut off.\n",
                fontSize = smallFont.times(0.9f),
                lineHeight = 0.06.sh,
                color = Color(0xFF757575),
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll)
            )

            Text(
                text = "",
                modifier = Modifier
                    .padding(top = 0.1.dh)
                    .height(0.05.dh)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {}
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0x33FFFFFF), Color.White)
                        )
                    )
            )

        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.07.dw)
                .offset(y = 0.81.dh)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.ic_minus),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(0.06.dh)
                        .wrapContentWidth()
                        .padding(0.017.dh)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            viewModel.removeBuyItemsCount()
                        }
                )

                Spacer(modifier = Modifier.width(0.03.dw))
                AnimatedContent(
                    targetState = viewModel.buyItemsCount.value,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { height -> height } + fadeIn() with
                                    slideOutVertically { height -> -height } + fadeOut()
                        } else {
                            slideInVertically { height -> -height } + fadeIn() with
                                    slideOutVertically { height -> height } + fadeOut()
                        }.using(SizeTransform(clip = false))
                    }
                ) { targetCount ->
                    Text(
                        text = "$targetCount",
                        fontSize = smallFont.times(1.7f),
                        color = Color(0xFF595959),
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.width(0.03.dw))

                Image(
                    painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(0.06.dh)
                        .wrapContentWidth()
                        .padding(0.017.dh)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            viewModel.addBuyItemsCount()
                        }
                )
            }

            AnimatedContent(
                targetState = viewModel.isItemAddedToFavourite.value,
                contentAlignment = Alignment.Center
            ) { targetState ->
                Image(
                    painterResource(id = if (targetState) R.drawable.ic_favourite_selected else R.drawable.ic_favourite_unselected),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .offset(x = -(0.02.dw))
                        .height(0.08.dh)
                        .wrapContentWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            viewModel.setIsItemAddedToFavourite()
                        }
                        .padding(0.018.dh)

                )
            }
        }


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(0.07.dh)
                .padding(horizontal = 0.07.dw)
                .offset(y = 0.9.dh)
        ) {

            val color = Color(0xFF3D82AE)
            Image(
                painterResource(id = R.drawable.ic_add_to_cart),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .border(2.dp, color, RoundedCornerShape(0.05.dw))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.addToCard()
                    }
                    .padding(0.02.dh)
            )

            Spacer(modifier = Modifier.width(0.03.dw))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = color),
                shape = RoundedCornerShape(0.05.dw),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(id = R.string.buy_now).uppercase(),
                    fontSize = bigFont.times(0.5f),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }

        }

    }
}

@Composable
fun Background() {

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3D82AE))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.55.dh)
                .background(Color.White, RoundedCornerShape(topStart = 0.06.dw, topEnd = 0.06.dw))

        ) {}
    }
}