package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.home

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.AppProductsModel

sealed class HomeState {
    data object Idle : HomeState()
    data object Loading : HomeState()
    data class Success(val data: AppProductsModel) : HomeState()
    data class Error(val message: String) : HomeState()
}
