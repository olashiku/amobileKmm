package com.exquisite.a_mobile_kmm.feature.auth.presenter.otp

sealed class VerifyOtpState {
    object Idle: VerifyOtpState()
    object Loading: VerifyOtpState()
    data class Success(val data:String ):VerifyOtpState()
    data class Error(val message:String):VerifyOtpState()
}