package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_checkout

import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.PaymentModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.PaymentResponseModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form.DeepCleaningFormState

sealed class DeepCleaningCheckoutState {
    data object Idle : DeepCleaningCheckoutState()
    data object Loading : DeepCleaningCheckoutState()
    data class InitPaymentSuccess(val payment: PaymentModel) : DeepCleaningCheckoutState()
    data class CompletePaymentSuccess(val paymentResponse: PaymentResponseModel) : DeepCleaningCheckoutState()
    data class PaymentSuccess(val paymentResponse: PaymentResponseModel) : DeepCleaningCheckoutState()
    data class Error(val message: String) : DeepCleaningCheckoutState()
}
