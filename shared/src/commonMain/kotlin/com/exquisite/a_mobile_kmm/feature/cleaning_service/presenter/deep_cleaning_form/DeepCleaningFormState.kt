package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_form

import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.*

sealed class DeepCleaningFormState {
    data object Idle : DeepCleaningFormState()
    data object Loading : DeepCleaningFormState()
    data class PriceSuccess(val cleaningPriceModel: CleaningPriceModel) : DeepCleaningFormState()
    data class Error(val message: String) : DeepCleaningFormState()
}
