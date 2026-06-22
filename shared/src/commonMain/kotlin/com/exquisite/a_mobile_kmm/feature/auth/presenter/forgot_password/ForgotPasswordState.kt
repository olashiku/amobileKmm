package com.exquisite.a_mobile_kmm.feature.auth.presenter.forgot_password

sealed class ForgotPasswordState {
    object Loading:ForgotPasswordState()
    object Idle:ForgotPasswordState()
    data  class Error(val message:String):ForgotPasswordState()
    data  class Success(val uniqueRef:String):ForgotPasswordState()
}