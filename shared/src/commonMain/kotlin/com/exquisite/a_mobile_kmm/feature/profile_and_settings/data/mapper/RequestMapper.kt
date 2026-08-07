package com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.mapper

import com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.remote.request.ChangePasswordRequestDto
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.remote.request.EditProfileRequestDto
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.ChangePasswordRequest
import com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model.EditProfileRequest

fun EditProfileRequest.toDto(): EditProfileRequestDto {
    return EditProfileRequestDto(
        email = email,
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        customerId = customerId,
        profilePicture = profilePicture
    )
}

fun ChangePasswordRequest.toDto(): ChangePasswordRequestDto {
    return ChangePasswordRequestDto(
        customerId = customerId,
        oldPassword = oldPassword,
        newPassword = newPassword
    )
}
