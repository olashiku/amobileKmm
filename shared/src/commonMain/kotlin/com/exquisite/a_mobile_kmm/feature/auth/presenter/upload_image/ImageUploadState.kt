package com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image

sealed class ImageUploadState {
    object Loading:ImageUploadState()
    object Idle:ImageUploadState()
    data class Success(val url:String):ImageUploadState()
    data class Error(val message:String):ImageUploadState()
}