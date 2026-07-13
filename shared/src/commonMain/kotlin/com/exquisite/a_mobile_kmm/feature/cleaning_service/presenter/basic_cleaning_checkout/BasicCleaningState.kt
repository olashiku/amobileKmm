package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_checkout

import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.PaymentModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.PaymentResponseModel

sealed class BasicCleaningState {
    object Idle : BasicCleaningState()
    object Loading : BasicCleaningState()
    data class InitBasicCleaningSuccess(val data: PaymentModel) : BasicCleaningState()
    data class CompleteBasicCleaningSuccess(val data: PaymentResponseModel) : BasicCleaningState()
    data class Error(val message: String) : BasicCleaningState()
}