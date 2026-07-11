package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetCleaningPriceResponseDto(
    val responseMessage: String = "",
    val responseCode: String = "",
    @Serializable(with = CleaningPriceDataSerializer::class)
    val data: CleaningPriceDto? = null
)

object CleaningPriceDataSerializer :
    EmptyObjectAsNullSerializer<CleaningPriceDto>(CleaningPriceDto.serializer())

@Serializable
data class CleaningPriceDto(
    val amount: Double,
    val apartmentType: CleaningPriceApartmentTypeDto,
    val cleaningType: CleaningPriceCleaningTypeDto,
    val id: Int,
    val numberOfRooms: CleaningPriceNumberOfRoomsDto,
    val region: CleaningPriceRegionDto
)

@Serializable
data class CleaningPriceApartmentTypeDto(
    val id: Int,
    val name: String
)

@Serializable
data class CleaningPriceCleaningTypeDto(
    val id: Int,
    val name: String
)

@Serializable
data class CleaningPriceNumberOfRoomsDto(
    val id: Int,
    val name: String
)

@Serializable
data class CleaningPriceRegionDto(
    val id: Int,
    val name: String
)

