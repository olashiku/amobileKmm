package com.exquisite.a_mobile_kmm.feature.auth.presenter.otp

sealed class OtpState {
    object Idle: OtpState()
    object Loading: OtpState()
    data class Success(val data:String ):OtpState()
    data class Error(val message:String):OtpState()
}