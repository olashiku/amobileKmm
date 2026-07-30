package com.exquisite.a_mobile_kmm.feature.training.presenter.training_registration

import androidx.lifecycle.ViewModel
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingRegistrationModel
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.CompleteEnrollTrainingUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.EnrollTrainingByAccountBalanceUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.InitEnrollTrainingUseCase
import com.exquisite.a_mobile_kmm.feature.training.presenter.training_checkout.TrainingRegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrainingRegistrationViewModel : ViewModel() {

    private var _trainingRegistrationState = MutableStateFlow<TrainingRegistrationState>(TrainingRegistrationState.Idle)
    val trainingRegistrationState = _trainingRegistrationState.asStateFlow()

    private var _persistedFormData = MutableStateFlow(TrainingRegistrationModel())
    val persistedFormData = _persistedFormData.asStateFlow()


    fun saveFormData(
        fullName: String,
        email: String,
        phone: String,
        address: String,
        gender: String
    ) {
        _persistedFormData.value = TrainingRegistrationModel(
            fullName = fullName,
            email = email,
            phone = phone,
            address = address,
            gender = gender
        )
    }


    fun clearState() {
        _trainingRegistrationState.value = TrainingRegistrationState.Idle
    }
}
