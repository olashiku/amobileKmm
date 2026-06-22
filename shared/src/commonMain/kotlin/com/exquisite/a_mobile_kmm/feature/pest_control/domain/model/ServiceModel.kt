package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

data class ServiceModel(
    val id: Int,
    val serviceName: String,
    val basePrice: Double,
    val extraRoomPrice: Double?,
    val createdAt: String,
    val updatedAt: String
)
