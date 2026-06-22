package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper

import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.*

fun FindAllRegionsResponseDto.toRegionModelList(): List<RegionModel>? {
    return data?.map { it.toRegionModel() }
}

fun RegionDto.toRegionModel(): RegionModel {
    return RegionModel(id = id, name = name)
}

fun FindLocationByRegionResponseDto.toLocationModelList(): List<LocationModel>? {
    return data?.map { it.toLocationModel() }
}

fun LocationDto.toLocationModel(): LocationModel {
    return LocationModel(id = id, name = name, regionId = regionId)
}

fun FindApartmentTypeResponseDto.toApartmentTypeModelList(): List<ApartmentTypeModel>? {
    return data?.map { it.toApartmentTypeModel() }
}

fun ApartmentTypeDto.toApartmentTypeModel(): ApartmentTypeModel {
    return ApartmentTypeModel(id = id, name = name)
}

fun FindCleaningTypeResponseDto.toCleaningTypeModelList(): List<CleaningTypeModel>? {
    return data?.map { it.toCleaningTypeModel() }
}

fun CleaningTypeDto.toCleaningTypeModel(): CleaningTypeModel {
    return CleaningTypeModel(id = id, name = name)
}

fun FindNumberOfRoomsResponseDto.toNumberOfRoomsModelList(): List<NumberOfRoomsModel>? {
    return data?.map { it.toNumberOfRoomsModel() }
}

fun NumberOfRoomsDto.toNumberOfRoomsModel(): NumberOfRoomsModel {
    return NumberOfRoomsModel(id = id, name = name)
}

fun GetCleaningPriceResponseDto.toCleaningPriceModel(): CleaningPriceModel? {
    val priceData = data ?: return null
    return CleaningPriceModel(price = priceData.price, currency = priceData.currency)
}

fun DebitFromWalletDeepCleaningPaymentResponseDto.toPaymentResponseModel(): PaymentResponseModel {
    return PaymentResponseModel(message = responseMessage)
}

fun InitDeepCleaningPaymentResponseDto.toPaymentModel(): PaymentModel? {
    val paymentData = data ?: return null
    return PaymentModel(
        authorizationUrl = paymentData.authorization_url,
        accessCode = paymentData.access_code,
        reference = paymentData.reference
    )
}

fun CompleteDeepCleaningPaymentResponseDto.toPaymentResponseModel(): PaymentResponseModel {
    return PaymentResponseModel(message = responseMessage)
}

fun GetBasicCleaningLocationsResponseDto.toBasicCleaningLocationModelList(): List<BasicCleaningLocationModel>? {
    return data?.map { it.toBasicCleaningLocationModel() }
}

fun BasicCleaningLocationDto.toBasicCleaningLocationModel(): BasicCleaningLocationModel {
    return BasicCleaningLocationModel(id = id, name = name, price = price)
}
