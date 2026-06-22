package com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration

import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerModel

sealed class CleanersRegistrationState {
    data object Idle : CleanersRegistrationState()
    data object Loading : CleanersRegistrationState()
    data class Success(val data: RegisterCleanerModel) : CleanersRegistrationState()
    data class Error(val message: String) : CleanersRegistrationState()
}
