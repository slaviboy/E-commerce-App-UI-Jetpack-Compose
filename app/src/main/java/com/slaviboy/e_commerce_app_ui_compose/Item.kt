package com.slaviboy.e_commerce_app_ui_compose

import androidx.compose.ui.graphics.Color

data class Item(
    val imageResId: Int,
    val name: String,
    val price: Float,
    val backgroundColor: Color,
    val currencySymbol: String = "$"
)