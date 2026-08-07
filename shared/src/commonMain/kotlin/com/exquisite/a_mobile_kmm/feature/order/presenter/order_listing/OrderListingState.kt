package com.exquisite.a_mobile_kmm.feature.order.presenter.order_listing

import com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrdersModel

sealed class OrderListingState {
    data object Idle : OrderListingState()
    data object Loading : OrderListingState()
    data class Success(
        val data: CustomerOrdersModel,
        val hasMore: Boolean = false,
        val isLoadingMore: Boolean = false
    ) : OrderListingState()
    data class Error(val message: String) : OrderListingState()
}
