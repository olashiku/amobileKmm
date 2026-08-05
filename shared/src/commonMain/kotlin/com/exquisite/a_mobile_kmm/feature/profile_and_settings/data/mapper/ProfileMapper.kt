package com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.mapper

import com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.ProfileResponseModel

fun EditProfileResponseDto.toProfileResponseModel(): ProfileResponseModel {
    return ProfileResponseModel(message = responseMessage)
}

fun ChangePasswordResponseDto.toProfileResponseModel(): ProfileResponseModel {
    return ProfileResponseModel(message = responseMessage)
}
