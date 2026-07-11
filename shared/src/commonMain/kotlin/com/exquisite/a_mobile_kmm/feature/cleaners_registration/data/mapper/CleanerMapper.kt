package com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.mapper

import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.request.RegisterCleanerRequestDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.response.RegisterCleanerResponseDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerModel
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest

fun RegisterCleanerRequest.toRequestDto(): RegisterCleanerRequestDto {
    return RegisterCleanerRequestDto(
        customerId = customerId,
        fullName = fullName,
        email = email,
        phone = phone,
        address = address,
        resumeUrl = resumeUrl,
        pictureUrl = pictureUrl,
        gender = gender,
        employmentStatus = employmentStatus,
        experienceLevel = experienceLevel
    )
}

fun RegisterCleanerResponseDto.toRegisterCleanerModel(): RegisterCleanerModel {
    return RegisterCleanerModel(message = responseMessage)
}
