package com.exquisite.a_mobile_kmm.feature.auth.presenter.create_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.CompleteForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreatePasswordViewModel(private val completeForgotPasswordUseCase: CompleteForgotPasswordUseCase) :
    ViewModel() {
    private var _confirmPasswordState = MutableStateFlow<ConfirmPasswordState>(ConfirmPasswordState.Idle)
    val confirmPasswordState = _confirmPasswordState.asStateFlow()

    fun confirmPassword(
        uniqueRef: String,
        password: String,
        otp: String
    ) {
        viewModelScope.launch {
            _confirmPasswordState.value =   ConfirmPasswordState.Loading

            completeForgotPasswordUseCase.invoke(uniqueRef,password,otp).collect{ result ->
                when(result){
                    is UseCaseResult.Success ->{
                        _confirmPasswordState.value =   ConfirmPasswordState.Success(result.data)
                    }
                    is UseCaseResult.Error ->{
                        _confirmPasswordState.value =   ConfirmPasswordState.Error(result.message)
                    }

                }
            }
        }

    }

    fun reset(){
        _confirmPasswordState.value =   ConfirmPasswordState.Idle
    }
}