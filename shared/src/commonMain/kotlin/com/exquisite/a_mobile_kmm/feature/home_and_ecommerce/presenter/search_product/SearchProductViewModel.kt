package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.search_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetAllProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchProductViewModel(private val getAllProductsUseCase: GetAllProductsUseCase) : ViewModel() {

    private var _searchProductState = MutableStateFlow<SearchProductState>(SearchProductState.Idle)
    val searchProductState = _searchProductState.asStateFlow()

    fun searchProducts(pageNumber: Int, pageSize: Int, searchTerm: String) {
        viewModelScope.launch {
            _searchProductState.value = SearchProductState.Loading
            getAllProductsUseCase.invoke(pageNumber, pageSize, searchTerm).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _searchProductState.value = SearchProductState.Success(response.data)
                    is UseCaseResult.Error ->
                        _searchProductState.value = SearchProductState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _searchProductState.value = SearchProductState.Idle
    }
}
