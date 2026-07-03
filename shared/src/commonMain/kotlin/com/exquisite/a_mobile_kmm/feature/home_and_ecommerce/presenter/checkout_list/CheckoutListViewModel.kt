package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel
import com.exquisite.a_mobile_kmm.feature.cart.domain.usecase.CartUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CreateOrderRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.CreateOrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutListViewModel(private val createOrderUseCase: CreateOrderUseCase,
                            val cartUseCase: CartUseCase) : ViewModel() {

    private var _checkoutListState = MutableStateFlow<CheckoutListState>(CheckoutListState.Idle)
    val checkoutListState = _checkoutListState.asStateFlow()

    init{
        getAllItems()
    }


    val _cartState = MutableStateFlow<List<CartModel>>(emptyList())
    val cartState =  _cartState.asStateFlow()

    fun getAllItems(){
        viewModelScope.launch {
            cartUseCase.getAllItems().collect{
                _cartState.value = it
            }
        }
    }


    fun createOrder(request: CreateOrderRequestDto) {
        viewModelScope.launch {
            _checkoutListState.value = CheckoutListState.Loading
            createOrderUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _checkoutListState.value = CheckoutListState.Success(response.data)
                    is UseCaseResult.Error ->
                        _checkoutListState.value = CheckoutListState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _checkoutListState.value = CheckoutListState.Idle
    }
}
