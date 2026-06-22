package com.exquisite.a_mobile_kmm.feature.septic.presenter.septic_residential_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.CompleteSepticPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.DebitFromAccountSepticRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.data.remote.request.InitSepticPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.CompleteSepticPaymentUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.DebitFromAccountSepticUseCase
import com.exquisite.a_mobile_kmm.feature.septic.domain.usecase.InitSepticPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SepticResidentialCheckoutViewModel(
    private val initSepticPaymentUseCase: InitSepticPaymentUseCase,
    private val debitFromAccountSepticUseCase: DebitFromAccountSepticUseCase,
    private val completeSepticPaymentUseCase: CompleteSepticPaymentUseCase
) : ViewModel() {

    private var _septicResidentialCheckoutState = MutableStateFlow<SepticResidentialCheckoutState>(SepticResidentialCheckoutState.Idle)
    val septicResidentialCheckoutState = _septicResidentialCheckoutState.asStateFlow()

    fun initPayment(request: InitSepticPaymentRequestDto) {
        viewModelScope.launch {
            _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Loading
            initSepticPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun debitFromWallet(request: DebitFromAccountSepticRequestDto) {
        viewModelScope.launch {
            _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Loading
            debitFromAccountSepticUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.DebitWalletSuccess(response.data)
                    is UseCaseResult.Error -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(request: CompleteSepticPaymentRequestDto) {
        viewModelScope.launch {
            _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Loading
            completeSepticPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.CompletePaymentSuccess(response.data)
                    is UseCaseResult.Error -> _septicResidentialCheckoutState.value = SepticResidentialCheckoutState.Error(response.message)
                }
            }
        }
    }
}
