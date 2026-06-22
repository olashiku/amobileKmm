package com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.mapper

import com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.response.RegisterCleanerResponseDto
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerModel

fun RegisterCleanerResponseDto.toRegisterCleanerModel(): RegisterCleanerModel {
    return RegisterCleanerModel(message = responseMessage)
}
