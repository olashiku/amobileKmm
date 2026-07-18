package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

data class ServiceModel(
    val id: Int = 0,
    val serviceName: String = "",
    val basePrice: Double = 0.0,
    val extraRoomPrice: Double?= 0.0,
    val createdAt: String = "",
    val updatedAt: String = ""
)
