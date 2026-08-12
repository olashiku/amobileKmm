package com.exquisite.a_mobile_kmm.feature.employee.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateBookingImagesRequestDto(
    val employeeId: String,
    val bookingId: String,
    val images: List<String>
)
