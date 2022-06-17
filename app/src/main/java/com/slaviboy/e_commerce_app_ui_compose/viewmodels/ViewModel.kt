package com.slaviboy.e_commerce_app_ui_compose.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.slaviboy.e_commerce_app_ui_compose.Item
import com.slaviboy.e_commerce_app_ui_compose.R
import com.slaviboy.e_commerce_app_ui_compose.composable.Screen

class ViewModel : ViewModel() {

    private val _items = mutableStateOf<List<Item>>(requestItems())
    val items: State<List<Item>> = _items

    private val _itemsPosition = mutableStateOf(0)
    val itemsPosition: State<Int> = _itemsPosition

    fun setItemsPosition(itemsPosition: Int) {
        _itemsPosition.value = itemsPosition
    }


    private val _itemDescription = mutableStateOf("Aristocratic Hand Bag")
    val itemDescription: State<String> = _itemDescription

    fun setItemsDescription(itemDescription: String) {
        _itemDescription.value = itemDescription
    }

    private val _itemName = mutableStateOf("Office Code")
    val itemName: State<String> = _itemName

    fun setItemsName(itemName: String) {
        _itemName.value = itemName
    }

    private val _itemPrice = mutableStateOf(123.3f)
    val itemPrice: State<Float> = _itemPrice

    fun setItemsPrice(itemPrice: Float) {
        _itemPrice.value = itemPrice
    }

    fun getItemPrice(): String {
        return "$${itemPrice.value}"
    }


    private val _isItemAddedToFavourite = mutableStateOf(true)
    val isItemAddedToFavourite: State<Boolean> = _isItemAddedToFavourite

    fun setIsItemAddedToFavourite() {
        _isItemAddedToFavourite.value = !_isItemAddedToFavourite.value
    }

    private val _buyItemsCount = mutableStateOf(0)
    val buyItemsCount: State<Int> = _buyItemsCount

    fun removeBuyItemsCount() {
        if (_buyItemsCount.value > 0) {
            _buyItemsCount.value -= 1
        }
    }

    fun addBuyItemsCount() {
        if (_buyItemsCount.value < 100) {
            _buyItemsCount.value += 1
        }
    }

    /**
     * Simulate request to External/Local Database
     */
    fun requestItems(): List<Item> {
        return listOf(
            Item(R.drawable.bag1, "Your bag name", 234f, Color(0xFFFB7883)),
            Item(R.drawable.bag2, "Your bag name", 234f, Color(0xFFE6B398)),
            Item(R.drawable.bag3, "Your bag name", 234f, Color(0xFF5ABBD5)),
            Item(R.drawable.bag4, "Your bag name", 234f, Color(0xFFD97B7C)),
            Item(R.drawable.bag5, "Your bag name", 234f, Color(0xFFDCAB92)),
            Item(R.drawable.bag6, "Your bag name", 234f, Color(0xFF60B2CB)),
            Item(R.drawable.bag7, "Your bag name", 234f, Color(0xFF817874)),
            Item(R.drawable.bag8, "Your bag name", 234f, Color(0xFFE0CDBB)),
            Item(R.drawable.bag9, "Your bag name", 234f, Color(0xFFC0A27A)),
            Item(R.drawable.bag10, "Your bag name", 234f, Color(0xFFD9D6D6)),
            Item(R.drawable.bag11, "Your bag name", 234f, Color(0xFFAF9F8A)),
            Item(R.drawable.bag12, "Your bag name", 234f, Color(0xFFC8A07D))
        )
    }

    fun addToCard() {

    }


    private val _invisibleClickedImage = mutableStateOf<Item?>(null)
    val invisibleClickedImage: State<Item?> = _invisibleClickedImage

    fun setInvisibleClickedImage(item: Item?) {
        _invisibleClickedImage.value = item
    }


    private val _sharedImageFromCoord = mutableStateOf(Rect.Zero)
    val sharedImageFromCoord: State<Rect> = _sharedImageFromCoord

    fun setSharedImageFromCoord(sharedImageFromCoord: Rect) {
        _sharedImageFromCoord.value = sharedImageFromCoord
    }


    private val _sharedImageToCoord = mutableStateOf(Rect.Zero)
    val sharedImageToCoord: State<Rect> = _sharedImageToCoord

    fun setSharedImageToCoord(sharedImageToCoord: Rect) {
        _sharedImageToCoord.value = sharedImageToCoord
    }


    private val _setSharedImageResId = mutableStateOf(R.drawable.ic_none)
    val setSharedImageResId: State<Int> = _setSharedImageResId

    fun setSharedImageResId(setSharedImageResId: Int) {
        _setSharedImageResId.value = setSharedImageResId
    }


    private val _changeSharedImagePositionFrom = mutableStateOf<Screen>(Screen.Detail)
    val changeSharedImagePositionFrom: State<Screen> = _changeSharedImagePositionFrom

    fun setChangeSharedImagePositionFrom(screen: Screen) {
        _changeSharedImagePositionFrom.value = screen
    }

    private val _sharedImageOpacity = mutableStateOf(0f)
    val sharedImageOpacity: State<Float> = _sharedImageOpacity

    fun setSharedImageOpacity(sharedImageOpacity: Float) {
        _sharedImageOpacity.value = sharedImageOpacity
    }


    private val _itemImageOpacity = mutableStateOf(1f)
    val itemImageOpacity: State<Float> = _itemImageOpacity

    fun setItemImageOpacity(itemImageOpacity: Float) {
        _itemImageOpacity.value = itemImageOpacity
    }

    private val _enableItemsScroll = mutableStateOf(true)
    val enableItemsScroll: State<Boolean> = _enableItemsScroll

    fun setEnableItemsScroll(enableItemsScroll: Boolean) {
        _enableItemsScroll.value = enableItemsScroll
    }

}