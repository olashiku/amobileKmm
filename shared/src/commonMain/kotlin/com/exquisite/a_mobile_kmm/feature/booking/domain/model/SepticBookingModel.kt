package com.exquisite.a_mobile_kmm.feature.booking.domain.model

data class SepticBookingModel(
    val id: Int,
    val fullName: String,
    val phoneNo: String,
    val email: String,
    val address: String,
    val dateOfExcavation: String,
    val timeOfExcavation: String,
    val specialNote: String,
    val liter: Int,
    val amount: Double,
    val paymentStatus: String,
    val serviceStatus: String,
    val createdAt: String,
    val updatedAt: String
)
