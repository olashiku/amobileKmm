package com.exquisite.a_mobile_kmm.feature.training.presenter.training_registration

import com.exquisite.a_mobile_kmm.feature.training.domain.model.EnrollmentSuccessModel
import com.exquisite.a_mobile_kmm.feature.training.domain.model.InitEnrollTrainingModel

sealed class TrainingRegistrationState {
    data object Idle : TrainingRegistrationState()
    data object Loading : TrainingRegistrationState()
    data class InitEnrollSuccess(val data: InitEnrollTrainingModel) : TrainingRegistrationState()
    data class EnrollmentSuccess(val data: EnrollmentSuccessModel) : TrainingRegistrationState()
    data class Error(val message: String) : TrainingRegistrationState()
}
