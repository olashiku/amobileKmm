package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.search_product

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductsListModel

sealed class SearchProductState {
    data object Idle : SearchProductState()
    data object Loading : SearchProductState()
    data class Success(val data: ProductsListModel) : SearchProductState()
    data class Error(val message: String) : SearchProductState()
}
