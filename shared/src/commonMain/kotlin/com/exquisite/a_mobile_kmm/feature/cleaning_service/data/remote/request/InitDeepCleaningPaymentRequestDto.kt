package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitDeepCleaningPaymentRequestDto(
    val customerId: Int,
    val regionId: Int,
    val locationId: Int,
    val apartmentId: Int,
    val cleaningTypeId: Int,
    val numberOfRoomsId: Int,
    val amount: Double,
    val address: String
)
