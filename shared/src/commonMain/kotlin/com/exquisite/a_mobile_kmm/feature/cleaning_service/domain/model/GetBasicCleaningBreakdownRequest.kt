package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

data class GetBasicCleaningBreakdownRequest(
    val numberOfRooms: Int,
    val cleaningTime: String,
    val customerId: Int,
    val cleaningDate: List<String>
)
