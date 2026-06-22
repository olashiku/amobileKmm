package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.basic_cleaning_form

import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.BasicCleaningLocationModel

sealed class BasicCleaningFormState {
    data object Idle : BasicCleaningFormState()
    data object Loading : BasicCleaningFormState()
    data class Success(val locations: List<BasicCleaningLocationModel>) : BasicCleaningFormState()
    data class Error(val message: String) : BasicCleaningFormState()
}
