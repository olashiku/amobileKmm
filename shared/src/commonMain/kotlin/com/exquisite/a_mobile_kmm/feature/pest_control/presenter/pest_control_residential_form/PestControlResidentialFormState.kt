package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_form

import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlPriceModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.ServiceModel

sealed class PestControlResidentialFormState {
    data object Idle : PestControlResidentialFormState()
    data object Loading : PestControlResidentialFormState()
    data class ServiceListSuccess(val services: List<ServiceModel>) : PestControlResidentialFormState()
    data class PriceSuccess(val price: PestControlPriceModel) : PestControlResidentialFormState()
    data class Error(val message: String) : PestControlResidentialFormState()
}
