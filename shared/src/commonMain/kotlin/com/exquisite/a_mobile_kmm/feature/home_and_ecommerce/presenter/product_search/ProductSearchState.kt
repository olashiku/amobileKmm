package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_search

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductsListModel

sealed class ProductSearchState {
    data object Idle : ProductSearchState()
    data object Loading : ProductSearchState()
    data class Success(val data: ProductsListModel) : ProductSearchState()
    data class Error(val message: String) : ProductSearchState()
}
