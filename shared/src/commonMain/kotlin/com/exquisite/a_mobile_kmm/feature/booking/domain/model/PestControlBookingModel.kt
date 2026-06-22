package com.exquisite.a_mobile_kmm.feature.booking.domain.model

data class PestControlBookingModel(
    val id: Int,
    val preorder: Preorder,
    val address: String,
    val images: String,
    val propertyType: String,
    val isHotFogging: Boolean,
    val serviceDate: String,
    val inspectionDate: String,
    val serviceTime: String,
    val inspectionTime: String,
    val customerOwnVehicle: Boolean,
    val numberOfVehicles: Int,
    val extraNote: String,
    val paymentStatus: String,
    val serviceStatus: String,
    val createdAt: String,
    val updatedAt: String
)

data class Preorder(
    val id: Int,
    val numberOfRooms: Int,
    val service: Service,
    val customerId: Int,
    val uniqueRef: String,
    val amount: Double,
    val createdAt: String,
    val updatedAt: String
)

data class Service(
    val id: Int,
    val serviceName: String,
    val basePrice: Double,
    val extraRoomPrice: Double?,
    val createdAt: String,
    val updatedAt: String
)
