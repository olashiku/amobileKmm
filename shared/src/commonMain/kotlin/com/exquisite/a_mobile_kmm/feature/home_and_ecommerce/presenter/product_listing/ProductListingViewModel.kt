package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetProductsByCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListingViewModel(private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase) : ViewModel() {

    private var _productListingState = MutableStateFlow<ProductListingState>(ProductListingState.Idle)
    val productListingState = _productListingState.asStateFlow()

    fun loadProductsByCategory(categoryId: Int) {
        viewModelScope.launch {
            _productListingState.value = ProductListingState.Loading
            getProductsByCategoryUseCase.invoke(categoryId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _productListingState.value = ProductListingState.Success(response.data)
                    }

                    is UseCaseResult.Error -> {
                        _productListingState.value = ProductListingState.Error(response.message)
                    }
                }
            }
        }
    }

    fun clearState() {
        _productListingState.value = ProductListingState.Idle
    }
}
