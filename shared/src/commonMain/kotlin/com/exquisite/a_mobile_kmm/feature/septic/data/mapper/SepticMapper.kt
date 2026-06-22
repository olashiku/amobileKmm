package com.exquisite.a_mobile_kmm.feature.septic.data.mapper

import com.exquisite.a_mobile_kmm.feature.septic.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.septic.domain.model.*

fun GetSepticTruckSizeResponseDto.toSepticTruckSizeModelList(): List<SepticTruckSizeModel>? {
    return data?.map { it.toSepticTruckSizeModel() }
}

fun SepticTruckSizeDto.toSepticTruckSizeModel(): SepticTruckSizeModel {
    return SepticTruckSizeModel(id = id, liter = liter, price = price)
}

fun InitSepticPaymentResponseDto.toSepticPaymentModel(): SepticPaymentModel? {
    val paymentData = data ?: return null
    return SepticPaymentModel(first = paymentData.first, second = paymentData.second)
}

fun DebitFromAccountSepticResponseDto.toSepticResponseModel(): SepticResponseModel {
    return SepticResponseModel(message = responseMessage)
}

fun SendEnquiryResponseDto.toSepticResponseModel(): SepticResponseModel {
    return SepticResponseModel(message = responseMessage)
}

fun CompleteSepticPaymentResponseDto.toSepticResponseModel(): SepticResponseModel {
    return SepticResponseModel(message = responseMessage)
}
