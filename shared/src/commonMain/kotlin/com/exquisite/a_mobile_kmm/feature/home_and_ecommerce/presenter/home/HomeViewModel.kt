package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cart.domain.usecase.CartUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetAppProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getAppProductsUseCase: GetAppProductsUseCase,
    private val cartUseCase: CartUseCase, private val dataStore: AMobileDataStore) : ViewModel() {

    private var _homeState = MutableStateFlow<HomeState>(HomeState.Idle)
    val homeState = _homeState.asStateFlow()

    private var _cartState = MutableStateFlow<Int>(0)
    val cartState = _cartState.asStateFlow()

    private val _customerName = MutableStateFlow("")
    val customerName = _customerName.asStateFlow()


    private val _profilePicture = MutableStateFlow("")
    val profilePicture = _profilePicture.asStateFlow()
    init {
        loadProducts()
        getTotalQuantity()
        getCustomerName()
        getProfilePicture()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            getAppProductsUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _homeState.value = HomeState.Success(response.data)

                    is UseCaseResult.Error ->
                        _homeState.value = HomeState.Error(response.message)
                }
            }
        }
    }

    fun getCustomerName(){
       viewModelScope.launch{
           dataStore.getCustomerName().collect{
               _customerName.value = it
           }
       }
    }

    fun getProfilePicture(){
        viewModelScope.launch{
            dataStore.getProfilePicture().collect{
                _profilePicture.value = it
            }
        }
    }

    fun  getTotalQuantity(){
        viewModelScope.launch {
            cartUseCase.getTotalQuantity().collect{ quantity ->
                _cartState.value = quantity
            }
        }
    }

    fun clearState() {
        _homeState.value = HomeState.Idle
    }
}
