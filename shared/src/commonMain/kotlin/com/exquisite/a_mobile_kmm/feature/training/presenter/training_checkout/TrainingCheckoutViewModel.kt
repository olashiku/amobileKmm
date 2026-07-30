package com.exquisite.a_mobile_kmm.feature.training.presenter.training_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.training.domain.model.CompleteEnrollTrainingRequest
import com.exquisite.a_mobile_kmm.feature.training.domain.model.EnrollTrainingByAccountBalanceRequest
import com.exquisite.a_mobile_kmm.feature.training.domain.model.InitEnrollTrainingRequest
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.CompleteEnrollTrainingUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.EnrollTrainingByAccountBalanceUseCase
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.InitEnrollTrainingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TrainingCheckoutViewModel(
    private val initEnrollTrainingUseCase: InitEnrollTrainingUseCase,
    private val completeEnrollTrainingUseCase: CompleteEnrollTrainingUseCase,
    private val enrollTrainingByAccountBalanceUseCase: EnrollTrainingByAccountBalanceUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _trainingRegistrationState =
        MutableStateFlow<TrainingRegistrationState>(TrainingRegistrationState.Idle)
    val trainingRegistrationState = _trainingRegistrationState.asStateFlow()

    private val _transactionReference = MutableStateFlow("")
    val transactionReference = _transactionReference.asStateFlow()

    fun initEnrollTraining(
        trainingId: Int,
        fullName: String,
        email: String,
        phone: String,
        address: String,
        gender: String
    ) {

        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = InitEnrollTrainingRequest(
                trainingId, customerId.toInt(), fullName, email,
                phone, address, gender
            )
            _trainingRegistrationState.value = TrainingRegistrationState.Loading
            initEnrollTrainingUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _transactionReference.value = response.data.ref
                        _trainingRegistrationState.value =
                            TrainingRegistrationState.InitEnrollSuccess(response.data)

                    }

                    is UseCaseResult.Error ->
                        _trainingRegistrationState.value =
                            TrainingRegistrationState.Error(response.message)
                }
            }
        }
    }

    fun completeEnrollTraining(txnRef: String) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = CompleteEnrollTrainingRequest(
                customerId.toInt(),
                transactionReference.value,
                txnRef
            )
            _trainingRegistrationState.value = TrainingRegistrationState.Loading
            completeEnrollTrainingUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _trainingRegistrationState.value =
                            TrainingRegistrationState.EnrollmentSuccess(response.data)

                    is UseCaseResult.Error ->
                        _trainingRegistrationState.value =
                            TrainingRegistrationState.Error(response.message)
                }
            }
        }
    }

    fun enrollTrainingByAccountBalance(
        trainingId: Int,
        fullName: String,
        email: String,
        phone: String,
        address: String,
        gender: String
    ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
            val request = EnrollTrainingByAccountBalanceRequest(
                trainingId, customerId.toInt(), fullName, email,
                phone, address, gender
            )
            _trainingRegistrationState.value = TrainingRegistrationState.Loading
            enrollTrainingByAccountBalanceUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _trainingRegistrationState.value =
                            TrainingRegistrationState.EnrollmentSuccess(response.data)

                    is UseCaseResult.Error ->
                        _trainingRegistrationState.value =
                            TrainingRegistrationState.Error(response.message)
                }
            }
        }
    }

    fun clearError(){
        _trainingRegistrationState.value = TrainingRegistrationState.Idle
    }

    fun clearReference(){
        _transactionReference.value = ""
    }

}