package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitBasicCleaningPaymentRequestDto(
    val reference: String,
    val apartmentTypeId: String,
    val images: List<String>,
    val regionId: String,
    val locationId: String,
    val address: String,
    val customerId: String
)
