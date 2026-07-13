package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BasicCleaningBreakdownModel(
    val fee: Double,
    val result: BasicCleaningResultModel,
    val reference: String
)

@Serializable
data class BasicCleaningResultModel(
    val selectedDaysOfWeek: List<String>,
    val timeOfDay: String,
    val startDate: String,
    val endDate: String,
    val totalDays: Int,
    val allScheduledDates: List<String>
)
