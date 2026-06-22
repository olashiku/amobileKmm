package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.deliver_option

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CompletePaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.DebitFromWalletRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.InitPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.CompletePaymentUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.DebitFromWalletUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.InitPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeliverOptionViewModel(
    private val initPaymentUseCase: InitPaymentUseCase,
    private val debitFromWalletUseCase: DebitFromWalletUseCase,
    private val completePaymentUseCase: CompletePaymentUseCase
) : ViewModel() {

    private var _deliverOptionState = MutableStateFlow<DeliverOptionState>(DeliverOptionState.Idle)
    val deliverOptionState = _deliverOptionState.asStateFlow()

    fun initPayment(request: InitPaymentRequestDto) {
        viewModelScope.launch {
            _deliverOptionState.value = DeliverOptionState.Loading
            initPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _deliverOptionState.value = DeliverOptionState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _deliverOptionState.value = DeliverOptionState.Error(response.message)
                }
            }
        }
    }

    fun debitFromWallet(request: DebitFromWalletRequestDto) {
        viewModelScope.launch {
            _deliverOptionState.value = DeliverOptionState.Loading
            debitFromWalletUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _deliverOptionState.value = DeliverOptionState.PaymentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _deliverOptionState.value = DeliverOptionState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(request: CompletePaymentRequestDto) {
        viewModelScope.launch {
            _deliverOptionState.value = DeliverOptionState.Loading
            completePaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _deliverOptionState.value = DeliverOptionState.PaymentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _deliverOptionState.value = DeliverOptionState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _deliverOptionState.value = DeliverOptionState.Idle
    }
}
