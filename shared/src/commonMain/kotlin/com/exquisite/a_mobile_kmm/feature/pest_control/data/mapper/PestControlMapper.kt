package com.exquisite.a_mobile_kmm.feature.pest_control.data.mapper

import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request.*
import com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.*

// Response DTO to Domain Model mappers
fun RequestCommercialPestControlResponseDto.toPestControlResponseModel(): PestControlResponseModel {
    return PestControlResponseModel(message = responseMessage)
}

// Domain to DTO mappers for requests
fun RequestCommercialPestControlModel.toDto(): RequestCommercialPestControlRequestDto {
    return RequestCommercialPestControlRequestDto(
        customerId = customerId,
        companyName = companyName,
        companyEmail = companyEmail,
        companyAddress = companyAddress,
        availabilityDate = availabilityDate,
        availabilityTime = availabilityTime,
        recipientName = recipientName,
        recipientEmail = recipientEmail,
        recipientPhone = recipientPhone
    )
}

fun GetServiceListResponseDto.toServiceModelList(): List<ServiceModel>? {
    return data?.map { it.toServiceModel() }
}

fun ServiceDto.toServiceModel(): ServiceModel {
    return ServiceModel(
        id = id,
        serviceName = serviceName,
        basePrice = basePrice,
        extraRoomPrice = extraRoomPrice,
        createdAt = created_at,
        updatedAt = updated_at
    )
}

fun GetPestControlPriceResponseDto.toPestControlPriceModel(): PestControlPriceModel? {
    val priceData = data ?: return null
    return PestControlPriceModel(amount = priceData.amount)
}

fun DebitFromWalletPestControlResponseDto.toPestControlResponseModel(): PestControlResponseModel {
    return PestControlResponseModel(message = responseMessage)
}

fun InitPestControlPaymentResponseDto.toPestControlPaymentModel(): PestControlPaymentModel? {
    val paymentData = data ?: return null
    return PestControlPaymentModel(first = paymentData.first, second = paymentData.second)
}

fun CompletePestControlPaymentResponseDto.toPestControlResponseModel(): PestControlResponseModel {
    return PestControlResponseModel(message = responseMessage)
}

fun GetPestControlPriceModel.toDto(): GetPestControlPriceRequestDto {
    return GetPestControlPriceRequestDto(
        numberOfRooms = numberOfRooms,
        serviceId = serviceId,
        customerId = customerId
    )
}
