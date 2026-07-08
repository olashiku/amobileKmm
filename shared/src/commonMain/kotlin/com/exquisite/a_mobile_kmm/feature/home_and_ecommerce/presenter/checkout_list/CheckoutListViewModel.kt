package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.address.domain.model.AddressModel
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel
import com.exquisite.a_mobile_kmm.feature.cart.domain.usecase.CartUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CreateOrderRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.OrderProduct
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.CreateOrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CheckoutListViewModel(
    private val createOrderUseCase: CreateOrderUseCase,
    val cartUseCase: CartUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private var _checkoutListState = MutableStateFlow<CheckoutListState>(CheckoutListState.Idle)
    val checkoutListState = _checkoutListState.asStateFlow()

    private val _selectedAddress = MutableStateFlow<AddressModel?>(null)
    val selectedAddress = _selectedAddress.asStateFlow()

    init{
        getAllItems()
        loadSelectedAddress()
    }


    val _cartState = MutableStateFlow<List<CartModel>>(emptyList())
    val cartState =  _cartState.asStateFlow()

    private fun loadSelectedAddress() {
        viewModelScope.launch {
            dataStore.getSelectedAddress().collect { addressJson ->
                if (addressJson.isNotEmpty()) {
                    try {
                        _selectedAddress.value = json.decodeFromString<AddressModel>(addressJson)
                    } catch (e: Exception) {
                        _selectedAddress.value = null
                    }
                }
            }
        }
    }

    fun getAllItems(){
        viewModelScope.launch {
            cartUseCase.getAllItems().collect{
                _cartState.value = it
            }
        }
    }


    fun createOrder() {
        viewModelScope.launch {
            _checkoutListState.value = CheckoutListState.Loading

            val customerId = dataStore.getUserId().first()
            val addressId =  _selectedAddress.value?.id
            val products = _cartState.value.map{ OrderProduct(it.productId, it.quantity) }
            val request = CreateOrderRequest(customerId.toInt(), addressId ?: 0, products)
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
