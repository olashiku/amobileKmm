package com.exquisite.a_mobile_kmm.feature.auth.presenter.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private var _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    private var _rememberMeState = MutableStateFlow<Boolean>(false)
    val rememberMeState = _rememberMeState.asStateFlow()

     init{
         getRememberMeState()
     }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            loginUseCase.invoke(email, password).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _loginState.value = LoginState.Success(response.data)

                    is UseCaseResult.Error ->
                        _loginState.value = LoginState.Error(response.message)
                }
            }
        }
    }

    fun saveRememberMe(rememberMe: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.saveRememberMe(rememberMe)
        }
    }

    fun getRememberMeState() {
        viewModelScope.launch {
            loginUseCase.getRememberMe().collect { value ->
                _rememberMeState.value = value
            }
        }
    }

    fun clearState() {
        _loginState.value = LoginState.Idle
    }
}
