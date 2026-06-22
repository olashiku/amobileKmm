package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.CompleteDeepCleaningPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.InitDeepCleaningPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.CompleteDeepCleaningPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.usecase.InitDeepCleaningPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeepCleaningCheckoutViewModel(
    private val initDeepCleaningPaymentUseCase: InitDeepCleaningPaymentUseCase,
    private val completeDeepCleaningPaymentUseCase: CompleteDeepCleaningPaymentUseCase
) : ViewModel() {

    private var _deepCleaningCheckoutState = MutableStateFlow<DeepCleaningCheckoutState>(DeepCleaningCheckoutState.Idle)
    val deepCleaningCheckoutState = _deepCleaningCheckoutState.asStateFlow()

    fun initPayment(request: InitDeepCleaningPaymentRequestDto) {
        viewModelScope.launch {
            _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Loading
            initDeepCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(request: CompleteDeepCleaningPaymentRequestDto) {
        viewModelScope.launch {
            _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Loading
            completeDeepCleaningPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.CompletePaymentSuccess(response.data)
                    is UseCaseResult.Error -> _deepCleaningCheckoutState.value = DeepCleaningCheckoutState.Error(response.message)
                }
            }
        }
    }
}
