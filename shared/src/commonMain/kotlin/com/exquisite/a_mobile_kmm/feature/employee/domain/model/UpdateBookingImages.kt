package com.exquisite.a_mobile_kmm.feature.employee.domain.model

data class UpdateBookingImagesRequest(
    val employeeId: Int,
    val bookingId: Int,
    val images: List<String>
)

data class UpdateBookingImagesResult(
    val isSuccess: Boolean,
    val message: String
)
