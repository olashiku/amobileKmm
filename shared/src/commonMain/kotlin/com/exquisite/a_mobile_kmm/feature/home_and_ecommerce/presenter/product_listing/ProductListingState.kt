package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.product_listing

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductsListModel

sealed class ProductListingState {
    data object Idle : ProductListingState()
    data object Loading : ProductListingState()
    data class Success(val data: List<ProductItem>) : ProductListingState()
    data class Error(val message: String) : ProductListingState()
}
