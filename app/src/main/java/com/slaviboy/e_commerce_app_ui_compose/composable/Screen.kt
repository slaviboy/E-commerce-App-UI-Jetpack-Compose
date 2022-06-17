package com.slaviboy.e_commerce_app_ui_compose.composable

sealed class Screen {
    object Home : Screen()
    object Detail : Screen()
}