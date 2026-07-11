package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CleaningPriceModel(
    val id: Int,
    val amount: Double,
    val apartmentType: ApartmentTypeModel,
    val cleaningType: CleaningTypeModel,
    val numberOfRooms: NumberOfRoomsModel,
    val region: RegionModel
)
