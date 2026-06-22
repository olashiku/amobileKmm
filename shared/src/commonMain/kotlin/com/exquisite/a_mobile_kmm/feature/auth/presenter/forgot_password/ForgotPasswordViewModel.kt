package com.exquisite.a_mobile_kmm.feature.auth.presenter.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.InitForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val initForgotPasswordUseCase: InitForgotPasswordUseCase) :
    ViewModel() {

    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val forgotPasswordState = _forgotPasswordState.asStateFlow()


   operator fun invoke(email:String){
     viewModelScope.launch{
         _forgotPasswordState.value = ForgotPasswordState.Loading
         initForgotPasswordUseCase.invoke(email).collect{ result ->
           when(result){
               is UseCaseResult.Success ->{
                   _forgotPasswordState.value = ForgotPasswordState.Success(result.data)
               }
               is UseCaseResult.Error ->{
                   _forgotPasswordState.value = ForgotPasswordState.Error(result.message)
               }
           }

         }
     }
   }

    fun reset(){
        _forgotPasswordState.value = ForgotPasswordState.Idle

    }
}