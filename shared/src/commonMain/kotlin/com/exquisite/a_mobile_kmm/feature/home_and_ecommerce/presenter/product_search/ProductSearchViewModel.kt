package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetAllProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductSearchViewModel(private val getAllProductsUseCase: GetAllProductsUseCase) : ViewModel() {

    private var _productSearchState = MutableStateFlow<ProductSearchState>(ProductSearchState.Idle)
    val productSearchState = _productSearchState.asStateFlow()

    fun searchProducts(pageNumber: Int, pageSize: Int, searchTerm: String) {
        viewModelScope.launch {
            _productSearchState.value = ProductSearchState.Loading
            getAllProductsUseCase.invoke(pageNumber, pageSize, searchTerm).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _productSearchState.value = ProductSearchState.Success(response.data)

                    is UseCaseResult.Error ->
                        _productSearchState.value = ProductSearchState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _productSearchState.value = ProductSearchState.Idle
    }
}
