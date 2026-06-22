package com.exquisite.a_mobile_kmm.feature.profile.data.mapper

import com.exquisite.a_mobile_kmm.feature.profile.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.profile.domain.model.ProfileResponseModel

fun EditProfileResponseDto.toProfileResponseModel(): ProfileResponseModel {
    return ProfileResponseModel(message = responseMessage)
}

fun ChangePasswordResponseDto.toProfileResponseModel(): ProfileResponseModel {
    return ProfileResponseModel(message = responseMessage)
}
