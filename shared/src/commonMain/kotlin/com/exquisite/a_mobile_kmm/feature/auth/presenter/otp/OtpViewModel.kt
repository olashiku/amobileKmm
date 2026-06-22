package com.exquisite.a_mobile_kmm.feature.auth.presenter.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.ResendOtpUseCase
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.ValidateOtpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OtpViewModel(
    private val otpUseCase: ResendOtpUseCase,
    private val validateOtpUseCase: ValidateOtpUseCase
) : ViewModel() {

    private val _otpMutableStateFlow = MutableStateFlow<OtpState>(OtpState.Idle)
    private val _validateOtpState = MutableStateFlow<VerifyOtpState>(VerifyOtpState.Idle)
    val validateOtpStateFlow = _validateOtpState.asStateFlow()
    val otpMutableStateFlow = _otpMutableStateFlow.asStateFlow()


    fun resendOtp(uniqueRef: String) {
        viewModelScope.launch {
            _otpMutableStateFlow.value = OtpState.Loading
            otpUseCase.invoke(uniqueRef).collect { result ->
                when (result) {
                    is UseCaseResult.Success -> {
                        _otpMutableStateFlow.value = OtpState.Success(result.data)
                    }
                    is UseCaseResult.Error -> {
                        _otpMutableStateFlow.value = OtpState.Error(result.message)
                    }
                }
            }
        }
    }

    fun validateOtp(uniqueRef: String, otp: String) {
        viewModelScope.launch {
            _validateOtpState.value = VerifyOtpState.Loading
            validateOtpUseCase.invoke(uniqueRef, otp).collect { result ->

                when (result) {
                    is UseCaseResult.Success -> {
                        _validateOtpState.value = VerifyOtpState.Success(result.data)
                    }

                    is UseCaseResult.Error -> {
                        _validateOtpState.value = VerifyOtpState.Error(result.message)
                    }
                }
            }
        }
    }

    fun reset() {
        _otpMutableStateFlow.value = OtpState.Idle
        _validateOtpState.value = VerifyOtpState.Idle
    }
}