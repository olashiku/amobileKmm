package com.exquisite.a_mobile_kmm.feature.janitorial.data.mapper

import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.response.CreateJanitorialResponseDto
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.model.JanitorialResponseModel

fun CreateJanitorialResponseDto.toJanitorialResponseModel(): JanitorialResponseModel {
    return JanitorialResponseModel(message = responseMessage)
}
