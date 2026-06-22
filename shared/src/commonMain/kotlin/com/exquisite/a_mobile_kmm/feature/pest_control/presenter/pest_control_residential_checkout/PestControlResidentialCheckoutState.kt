package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_checkout

import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlPaymentModel
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.PestControlResponseModel

sealed class PestControlResidentialCheckoutState {
    data object Idle : PestControlResidentialCheckoutState()
    data object Loading : PestControlResidentialCheckoutState()
    data class DebitWalletSuccess(val response: PestControlResponseModel) : PestControlResidentialCheckoutState()
    data class InitPaymentSuccess(val payment: PestControlPaymentModel) : PestControlResidentialCheckoutState()
    data class CompletePaymentSuccess(val response: PestControlResponseModel) : PestControlResidentialCheckoutState()
    data class Error(val message: String) : PestControlResidentialCheckoutState()
}
