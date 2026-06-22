package com.exquisite.a_mobile_kmm.feature.booking.domain.model

data class CleaningBookingModel(
    val id: Int,
    val region: Region,
    val location: Location,
    val apartmentType: ApartmentType,
    val cleaningType: CleaningType,
    val numberOfRooms: NumberOfRooms,
    val amount: Double,
    val address: String,
    val serviceType: String,
    val customerImages: String,
    val employeeImages: String,
    val paymentStatus: String,
    val serviceStatus: String,
    val createdAt: String,
    val updatedAt: String
)

data class Region(
    val id: Int,
    val name: String
)

data class Location(
    val id: Int,
    val name: String
)

data class ApartmentType(
    val name: String,
    val id: Int
)

data class CleaningType(
    val id: Int,
    val name: String
)

data class NumberOfRooms(
    val id: Int,
    val name: String
)
