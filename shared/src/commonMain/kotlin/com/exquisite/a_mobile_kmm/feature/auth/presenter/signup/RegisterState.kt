package com.exquisite.a_mobile_kmm.feature.auth.presenter.signup

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading: RegisterState()
    data class Error(val message: String) : RegisterState()
    data class Success(val uniqueRef: String) : RegisterState()
}