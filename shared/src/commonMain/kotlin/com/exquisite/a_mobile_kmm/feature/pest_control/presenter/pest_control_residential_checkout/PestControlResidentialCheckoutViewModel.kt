package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control_residential_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.CompletePestControlPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.DebitFromWalletPestControlRequestDto
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.InitPestControlPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.CompletePestControlPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.DebitFromWalletPestControlUseCase
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.usecase.InitPestControlPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PestControlResidentialCheckoutViewModel(
    private val debitFromWalletPestControlUseCase: DebitFromWalletPestControlUseCase,
    private val initPestControlPaymentUseCase: InitPestControlPaymentUseCase,
    private val completePestControlPaymentUseCase: CompletePestControlPaymentUseCase
) : ViewModel() {

    private var _pestControlResidentialCheckoutState = MutableStateFlow<PestControlResidentialCheckoutState>(PestControlResidentialCheckoutState.Idle)
    val pestControlResidentialCheckoutState = _pestControlResidentialCheckoutState.asStateFlow()

    fun debitFromWallet(request: DebitFromWalletPestControlRequestDto) {
        viewModelScope.launch {
            _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Loading
            debitFromWalletPestControlUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.DebitWalletSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun initPayment(request: InitPestControlPaymentRequestDto) {
        viewModelScope.launch {
            _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Loading
            initPestControlPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(request: CompletePestControlPaymentRequestDto) {
        viewModelScope.launch {
            _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Loading
            completePestControlPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.CompletePaymentSuccess(response.data)
                    is UseCaseResult.Error -> _pestControlResidentialCheckoutState.value = PestControlResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }
}
