package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_commercial

import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlResponseModel

sealed class PestControlCommercialState {
    data object Idle : PestControlCommercialState()
    data object Loading : PestControlCommercialState()
    data class Success(val response: PestControlResponseModel) : PestControlCommercialState()
    data class Error(val message: String) : PestControlCommercialState()
}
