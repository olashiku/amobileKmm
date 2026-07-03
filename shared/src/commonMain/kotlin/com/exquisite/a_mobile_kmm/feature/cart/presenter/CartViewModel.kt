package com.exquisite.a_mobile_kmm.feature.cart.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel
import com.exquisite.a_mobile_kmm.feature.cart.domain.usecase.CartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(val cartUseCase: CartUseCase): ViewModel() {

    val _cartState = MutableStateFlow<List<CartModel>>(emptyList())
    val cartState =  _cartState.asStateFlow()

    fun getAllItems(){
        viewModelScope.launch {
            cartUseCase.getAllItems().collect{
                _cartState.value = it
            }
        }
    }

    fun addItem(productId:Int,productName:String,productImage:String,productPrice:Double,quantity:Int){
        val cartModel = CartModel(productId,productName,productImage,productPrice,quantity)
        viewModelScope.launch {
            cartUseCase.addItem(cartModel)
        }
    }


    fun removeItem(productId:Int,quantity:Int,currentQuantity:Int){
        viewModelScope.launch {
            cartUseCase.removeItem(productId,quantity,currentQuantity)
        }
    }

   fun  removeItemById(productId:Int){
       viewModelScope.launch {
           cartUseCase.removeItemById(productId)
       }
   }
}