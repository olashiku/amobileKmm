package com.exquisite.a_mobile_kmm.feature.auth.presenter.create_password

sealed class ConfirmPasswordState {
    object Loading :ConfirmPasswordState()
    object Idle :ConfirmPasswordState()
    data class  Success(val success:String) :ConfirmPasswordState()
    data class  Error(val message:String) :ConfirmPasswordState()
}