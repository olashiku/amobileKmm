package com.exquisite.a_mobile_kmm.feature.janitorial.data.mapper

import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.request.CreateJanitorialRequestDto
import com.exquisite.a_mobile_kmm.feature.janitorial.data.remote.response.CreateJanitorialResponseDto
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.model.CreateJanitorialRequestModel
import com.exquisite.a_mobile_kmm.feature.janitorial.domain.model.JanitorialResponseModel

fun CreateJanitorialResponseDto.toJanitorialResponseModel(): JanitorialResponseModel {
    return JanitorialResponseModel(message = responseMessage)
}

fun CreateJanitorialRequestModel.toCreateJanitorialRequestDto(): CreateJanitorialRequestDto {
    return CreateJanitorialRequestDto(
        customerId = customerId,
        companyName = companyName,
        companyEmail = companyEmail,
        companyAddress = companyAddress,
        availabilityDate = availabilityDate,
        availabilityTime = availabilityTime,
        resumptionTime = resumptionTime,
        buildingImage = buildingImage,
        phoneNo = phoneNo
    )
}
