package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_form

import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticTruckSizeModel

sealed class SepticResidentialFormState {
    data object Idle : SepticResidentialFormState()
    data object Loading : SepticResidentialFormState()
    data class GetTruckSizeSuccess(val data: List<SepticTruckSizeModel>) : SepticResidentialFormState()
    data class Error(val message: String) : SepticResidentialFormState()
}
