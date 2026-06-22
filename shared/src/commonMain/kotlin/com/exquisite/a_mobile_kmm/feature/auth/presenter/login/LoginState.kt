package com.exquisite.a_mobile_kmm.feature.auth.presenter.login

import com.exquisite.a_mobile_kmm.feature.auth.domain.model.LoginModel

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Error(val message: String) : LoginState()
    data class Success(val loginModel: LoginModel) : LoginState()
}
