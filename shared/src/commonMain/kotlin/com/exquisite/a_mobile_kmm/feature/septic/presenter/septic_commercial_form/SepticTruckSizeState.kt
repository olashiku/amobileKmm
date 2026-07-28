package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form

import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticTruckSizeModel

sealed class SepticTruckSizeState {
    data object Idle : SepticTruckSizeState()
    data object Loading : SepticTruckSizeState()
    data class GetTruckSizeSuccess(val data: List<SepticTruckSizeModel>) : SepticTruckSizeState()
    data class Error(val message: String) : SepticTruckSizeState()
}
