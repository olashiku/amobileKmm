package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_commercial_form

import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticResponseModel

sealed class SepticCommercialFormState {
    data object Idle : SepticCommercialFormState()
    data object Loading : SepticCommercialFormState()
    data class SendEnquirySuccess(val data: SepticResponseModel) : SepticCommercialFormState()
    data class Error(val message: String) : SepticCommercialFormState()
}
