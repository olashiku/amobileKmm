package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.GetAllProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductSearchViewModel(private val getAllProductsUseCase: GetAllProductsUseCase) : ViewModel() {

    private var _productSearchState = MutableStateFlow<ProductSearchState>(ProductSearchState.Idle)
    val productSearchState = _productSearchState.asStateFlow()

    private var currentSearchTerm: String? = null
    private var currentPage = 0
    private val pageSize = 10
    private var isLoadingMore = false
    private val allProducts = mutableListOf<ProductItem>()

    fun searchProducts(searchTerm: String? = null, resetSearch: Boolean = false) {
        // Prevent duplicate calls
        if (isLoadingMore) return

        if (resetSearch) {
            currentPage = 0
            allProducts.clear()
            currentSearchTerm = searchTerm
        }

        viewModelScope.launch {
            if (currentPage == 0) {
                _productSearchState.value = ProductSearchState.Loading
            } else {
                val currentState = _productSearchState.value
                if (currentState is ProductSearchState.Success) {

                    _productSearchState.value = currentState.copy(isLoadingMore = true)
                }
            }

            isLoadingMore = true
            getAllProductsUseCase.invoke(currentPage, pageSize, searchTerm ?: currentSearchTerm).collect { response ->
                isLoadingMore = false
                when (response) {
                    is UseCaseResult.Success -> {
                        val newProducts = response.data
                        allProducts.addAll(newProducts)
                        _productSearchState.value = ProductSearchState.Success(
                            data = allProducts.toList(),
                            currentPage = currentPage,
                            isLoadingMore = false,
                            hasMoreData = newProducts.size == pageSize
                        )
                    }

                    is UseCaseResult.Error ->
                        _productSearchState.value = ProductSearchState.Error(response.message)
                }
            }
        }
    }

    fun loadMoreProducts() {
        val currentState = _productSearchState.value
        if (currentState is ProductSearchState.Success &&
            currentState.hasMoreData &&
            !isLoadingMore) {
            currentPage++
            searchProducts(currentSearchTerm)
        }
    }

    fun clearState() {
        _productSearchState.value = ProductSearchState.Idle
        currentPage = 0
        allProducts.clear()
        currentSearchTerm = null
        isLoadingMore = false
    }
}
