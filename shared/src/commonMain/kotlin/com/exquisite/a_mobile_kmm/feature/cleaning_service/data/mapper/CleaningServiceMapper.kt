package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.mapper

import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request.*
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
    return LocationModel(id = id, name = name)
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
    return CleaningPriceModel(
        id = priceData.id,
        amount = priceData.amount,
        apartmentType = ApartmentTypeModel(
            id = priceData.apartmentType.id,
            name = priceData.apartmentType.name
        ),
        cleaningType = CleaningTypeModel(
            id = priceData.cleaningType.id,
            name = priceData.cleaningType.name
        ),
        numberOfRooms = NumberOfRoomsModel(
            id = priceData.numberOfRooms.id,
            name = priceData.numberOfRooms.name
        ),
        region = RegionModel(
            id = priceData.region.id,
            name = priceData.region.name
        )
    )
}

fun DebitFromWalletDeepCleaningPaymentResponseDto.toPaymentResponseModel(): PaymentResponseModel {
    return PaymentResponseModel(message = responseMessage)
}

fun InitDeepCleaningPaymentResponseDto.toPaymentModel(): PaymentModel? {
    val paymentData = data ?: return null
    return PaymentModel(
        paymentLink = paymentData.paymentLink,
        ref = paymentData.ref)
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

// Domain to DTO mappers for requests
fun InitDeepCleaningPaymentRequest.toDto(): InitDeepCleaningPaymentRequestDto {
    return InitDeepCleaningPaymentRequestDto(
        customerId = customerId,
        regionId = regionId,
        locationId = locationId,
        apartmentTypeId = apartmentTypeId,
        cleaningTypeId = cleaningTypeId,
        numberOfRoomsId = numberOfRoomsId,
        isPostConstruction = isPostConstruction,
        cleaningDate = cleaningDate,
        cleaningTime = cleaningTime,
        address = address,
        images = images
    )
}

fun CompleteDeepCleaningPaymentRequest.toDto(): CompleteDeepCleaningPaymentRequestDto {
    return CompleteDeepCleaningPaymentRequestDto(
        customerId = customerId,
        ref = ref,
        txnRef = txnRef
    )
}

fun DebitFromWalletDeepCleaningPaymentRequest.toDto(): DebitFromWalletDeepCleaningPaymentRequestDto {
    return DebitFromWalletDeepCleaningPaymentRequestDto(
        customerId = customerId,
        regionId = regionId,
        locationId = locationId,
        apartmentTypeId = apartmentTypeId,
        cleaningTypeId = cleaningTypeId,
        numberOfRoomsId = numberOfRoomsId,
        isPostConstruction = isPostConstruction,
        cleaningDate = cleaningDate,
        cleaningTime = cleaningTime,
        address = address,
        images = images
    )
}

// Basic Cleaning Response DTO to Model mappers
fun GetBasicCleaningBreakdownResponseDto.toBasicCleaningBreakdownModel(): BasicCleaningBreakdownModel? {
    val breakdownData = data ?: return null
    return BasicCleaningBreakdownModel(
        fee = breakdownData.fee,
        result = BasicCleaningResultModel(
            selectedDaysOfWeek = breakdownData.result.selectedDaysOfWeek,
            timeOfDay = breakdownData.result.timeOfDay,
            startDate = breakdownData.result.startDate,
            endDate = breakdownData.result.endDate,
            totalDays = breakdownData.result.totalDays,
            allScheduledDates = breakdownData.result.allScheduledDates
        ),
        reference = breakdownData.reference
    )
}

fun InitBasicCleaningPaymentResponseDto.toPaymentModel(): PaymentModel? {
    val paymentData = data ?: return null
    return PaymentModel(
        paymentLink = paymentData.paymentLink,
        ref = paymentData.ref
    )
}

fun DebitFromWalletBasicCleaningPaymentResponseDto.toPaymentResponseModel(): PaymentResponseModel {
    return PaymentResponseModel(message = responseMessage)
}

fun CompleteBasicCleaningPaymentResponseDto.toPaymentResponseModel(): PaymentResponseModel {
    return PaymentResponseModel(message = responseMessage)
}

// Basic Cleaning Request Domain to DTO mappers
fun GetBasicCleaningBreakdownRequest.toDto(): GetBasicCleaningBreakdownRequestDto {
    return GetBasicCleaningBreakdownRequestDto(
        numberOfRooms = numberOfRooms,
        cleaningTime = cleaningTime,
        customerId = customerId,
        cleaningDate = cleaningDate
    )
}

fun InitBasicCleaningPaymentRequest.toDto(): InitBasicCleaningPaymentRequestDto {
    return InitBasicCleaningPaymentRequestDto(
        reference = reference,
        apartmentTypeId = apartmentTypeId.toString(),
        images = images,
        regionId = regionId.toString(),
        locationId = locationId.toString(),
        address = address,
        customerId = customerId.toString()
    )
}

fun DebitFromWalletBasicCleaningPaymentRequest.toDto(): DebitFromWalletBasicCleaningPaymentRequestDto {
    return DebitFromWalletBasicCleaningPaymentRequestDto(
        reference = reference,
        apartmentTypeId = apartmentTypeId.toString(),
        images = images,
        regionId = regionId.toString(),
        locationId = locationId.toString(),
        address = address,
        customerId = customerId.toString()
    )
}

fun CompleteBasicCleaningPaymentRequest.toDto(): CompleteBasicCleaningPaymentRequestDto {
    return CompleteBasicCleaningPaymentRequestDto(
        customerId = customerId,
        ref = ref,
        txnRef = txnRef
    )
}
