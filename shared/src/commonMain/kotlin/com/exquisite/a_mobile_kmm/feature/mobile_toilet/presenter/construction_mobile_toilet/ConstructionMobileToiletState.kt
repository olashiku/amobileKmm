package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.construction_mobile_toilet

import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.ToiletResponseModel

sealed class ConstructionMobileToiletState {
    data object Idle : ConstructionMobileToiletState()
    data object Loading : ConstructionMobileToiletState()
    data class Success(val response: ToiletResponseModel) : ConstructionMobileToiletState()
    data class Error(val message: String) : ConstructionMobileToiletState()
}
