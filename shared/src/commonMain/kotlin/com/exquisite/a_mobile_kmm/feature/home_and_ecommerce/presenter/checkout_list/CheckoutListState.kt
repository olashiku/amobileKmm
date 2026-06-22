package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.checkout_list

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CreateOrderModel

sealed class CheckoutListState {
    data object Idle : CheckoutListState()
    data object Loading : CheckoutListState()
    data class Success(val data: CreateOrderModel) : CheckoutListState()
    data class Error(val message: String) : CheckoutListState()
}
