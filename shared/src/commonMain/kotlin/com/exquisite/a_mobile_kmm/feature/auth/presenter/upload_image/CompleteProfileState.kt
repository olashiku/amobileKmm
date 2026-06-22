package com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image


sealed class CompleteProfileState {
    object Loading:CompleteProfileState()
    object Idle:CompleteProfileState()
    data class Success(val data:String):CompleteProfileState()
    data class Error(val message:String):CompleteProfileState()
}