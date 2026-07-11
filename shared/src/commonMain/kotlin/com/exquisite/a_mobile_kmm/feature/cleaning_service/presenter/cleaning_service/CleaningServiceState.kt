package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.cleaning_service

sealed class CleaningServiceState {
    data object Idle : CleaningServiceState()
    data object Loading : CleaningServiceState()
    data class Success(val isEligible: Boolean) : CleaningServiceState()
    data class Error(val message: String) : CleaningServiceState()
}
