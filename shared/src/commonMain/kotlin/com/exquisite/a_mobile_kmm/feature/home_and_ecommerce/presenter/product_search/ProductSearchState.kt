package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductsListModel

sealed class ProductSearchState {
    data object Idle : ProductSearchState()
    data object Loading : ProductSearchState()
    data class Success(
        val data: List<ProductItem>,
        val currentPage: Int = 1,
        val isLoadingMore: Boolean = false,
        val hasMoreData: Boolean = true
    ) : ProductSearchState()
    data class Error(val message: String) : ProductSearchState()
}
