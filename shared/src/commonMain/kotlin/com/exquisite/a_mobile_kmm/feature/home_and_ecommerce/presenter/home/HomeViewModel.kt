package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetAppProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getAppProductsUseCase: GetAppProductsUseCase) : ViewModel() {

    private var _homeState = MutableStateFlow<HomeState>(HomeState.Idle)
    val homeState = _homeState.asStateFlow()

    init {
        loadProducts()
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

    fun clearState() {
        _homeState.value = HomeState.Idle
    }
}
