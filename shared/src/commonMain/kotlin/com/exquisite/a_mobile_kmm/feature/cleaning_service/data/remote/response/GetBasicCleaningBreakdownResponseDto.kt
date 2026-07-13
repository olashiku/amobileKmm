package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GetBasicCleaningBreakdownResponseDto(
    val data: BasicCleaningBreakdownDataDto,
    val responseMessage: String,
    val responseCode: String
)

@Serializable
data class BasicCleaningBreakdownDataDto(
    val fee: Double,
    val result: BasicCleaningResultDto,
    val reference: String
)

@Serializable
data class BasicCleaningResultDto(
    val selectedDaysOfWeek: List<String>,
    val timeOfDay: String,
    val startDate: String,
    val endDate: String,
    val totalDays: Int,
    val allScheduledDates: List<String>
)
