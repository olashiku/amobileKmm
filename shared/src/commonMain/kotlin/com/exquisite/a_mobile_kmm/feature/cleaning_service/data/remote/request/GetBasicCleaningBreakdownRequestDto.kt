package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class GetBasicCleaningBreakdownRequestDto(
    val numberOfRooms: Int,
    val cleaningTime: String,
    val customerId: Int,
    val cleaningDate: List<String>
)
