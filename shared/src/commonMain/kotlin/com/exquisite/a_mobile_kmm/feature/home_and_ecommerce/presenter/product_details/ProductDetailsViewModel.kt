package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel
import com.exquisite.a_mobile_kmm.feature.cart.domain.usecase.CartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel( private val cartUseCase: CartUseCase) : ViewModel() {

    private var _cartState = MutableStateFlow<Int>(0)
    val cartState = _cartState.asStateFlow()

    init{
        getTotalQuantity()
    }

    fun addToCart(cartModel : CartModel){
        viewModelScope.launch {
            cartUseCase.addItem(cartModel)
        }
    }


    fun  getTotalQuantity(){
        viewModelScope.launch {
            cartUseCase.getTotalQuantity().collect{ quantity ->
                _cartState.value = quantity
            }
        }
    }

}
