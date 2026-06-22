package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_checkout

import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticPaymentModel
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.SepticResponseModel

sealed class SepticResidentialCheckoutState {
    data object Idle : SepticResidentialCheckoutState()
    data object Loading : SepticResidentialCheckoutState()
    data class InitPaymentSuccess(val data: SepticPaymentModel) : SepticResidentialCheckoutState()
    data class DebitWalletSuccess(val data: SepticResponseModel) : SepticResidentialCheckoutState()
    data class CompletePaymentSuccess(val data: SepticResponseModel) : SepticResidentialCheckoutState()
    data class Error(val message: String) : SepticResidentialCheckoutState()
}
