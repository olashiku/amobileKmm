package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.mapper

import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.*

fun RequestForConstructionResponseDto.toToiletResponseModel(): ToiletResponseModel {
    return ToiletResponseModel(message = responseMessage)
}

fun InitToiletPaymentResponseDto.toToiletPaymentModel(): ToiletPaymentModel? {
    val paymentData = data ?: return null
    return ToiletPaymentModel(first = paymentData.first, second = paymentData.second)
}

fun GetToiletPriceResponseDto.toToiletPriceModel(): ToiletPriceModel? {
    val priceData = data ?: return null
    return ToiletPriceModel(
        numberOfDays = priceData.numberOfDays,
        discountGiven = priceData.discountGiven,
        overnight = priceData.overnight,
        totalNumberOfGuests = priceData.totalNumberOfGuests,
        totalAmount = priceData.totalAmount,
        recommendedNumberOfStandardToilets = priceData.recommendedNumberOfStandardToilets,
        recommendedNumberOfVipToilets = priceData.recommendedNumberOfVipToilets,
        uniqueRef = priceData.uniqueRef
    )
}

fun CompleteToiletPaymentResponseDto.toToiletResponseModel(): ToiletResponseModel {
    return ToiletResponseModel(message = responseMessage)
}

fun DebitFromAccountResponseDto.toToiletResponseModel(): ToiletResponseModel {
    return ToiletResponseModel(message = responseMessage)
}

fun GetStandardToiletsListResponseDto.toStandardToiletModelList(): List<StandardToiletModel>? {
    return data?.map { it.toStandardToiletModel() }
}

fun StandardToiletDto.toStandardToiletModel(): StandardToiletModel {
    return StandardToiletModel(id = id, minimum = minimum, maximum = maximum, quantity = quantity)
}

fun GetEventTypeResponseDto.toEventTypeModelList(): List<EventTypeModel>? {
    return data?.map { it.toEventTypeModel() }
}

fun EventTypeDto.toEventTypeModel(): EventTypeModel {
    return EventTypeModel(id = id, name = name)
}

fun CheckToiletAvailabilityResponseDto.toToiletAvailabilityModel(): ToiletAvailabilityModel? {
    val availabilityData = data ?: return null
    return ToiletAvailabilityModel(
        availableStandardToilets = availabilityData.availableStandardToilets,
        availableVipToilet = availabilityData.availableVipToilet,
        canPurchase = availabilityData.canPurchase
    )
}
