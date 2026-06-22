package com.exquisite.a_mobile_kmm.feature.training.presenter.training_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.CompleteEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.EnrollTrainingByAccountBalanceRequestDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.request.InitEnrollTrainingRequestDto
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.CompleteEnrollTrainingUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.EnrollTrainingByAccountBalanceUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.InitEnrollTrainingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainingRegistrationViewModel(
    private val initEnrollTrainingUseCase: InitEnrollTrainingUseCase,
    private val completeEnrollTrainingUseCase: CompleteEnrollTrainingUseCase,
    private val enrollTrainingByAccountBalanceUseCase: EnrollTrainingByAccountBalanceUseCase
) : ViewModel() {

    private var _trainingRegistrationState = MutableStateFlow<TrainingRegistrationState>(TrainingRegistrationState.Idle)
    val trainingRegistrationState = _trainingRegistrationState.asStateFlow()

    fun initEnrollTraining(request: InitEnrollTrainingRequestDto) {
        viewModelScope.launch {
            _trainingRegistrationState.value = TrainingRegistrationState.Loading
            initEnrollTrainingUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _trainingRegistrationState.value = TrainingRegistrationState.InitEnrollSuccess(response.data)
                    is UseCaseResult.Error ->
                        _trainingRegistrationState.value = TrainingRegistrationState.Error(response.message)
                }
            }
        }
    }

    fun completeEnrollTraining(request: CompleteEnrollTrainingRequestDto) {
        viewModelScope.launch {
            _trainingRegistrationState.value = TrainingRegistrationState.Loading
            completeEnrollTrainingUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _trainingRegistrationState.value = TrainingRegistrationState.EnrollmentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _trainingRegistrationState.value = TrainingRegistrationState.Error(response.message)
                }
            }
        }
    }

    fun enrollTrainingByAccountBalance(request: EnrollTrainingByAccountBalanceRequestDto) {
        viewModelScope.launch {
            _trainingRegistrationState.value = TrainingRegistrationState.Loading
            enrollTrainingByAccountBalanceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _trainingRegistrationState.value = TrainingRegistrationState.EnrollmentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _trainingRegistrationState.value = TrainingRegistrationState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _trainingRegistrationState.value = TrainingRegistrationState.Idle
    }
}
