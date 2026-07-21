package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

data class InitPestControlPaymentRequest(
    val uniqueRef: String,
    val customerId: Int,
    val address: String,
    val images: List<String>,
    val apartmentTypeId: Int,
    val isHotFogging: Boolean,
    val serviceDate: String,
    val inspectionDate: String,
    val serviceTime: String,
    val inspectionTime: String,
    val extraNote: String,
    val customerOwnVehicle: Boolean,
    val numberOfVehicles: Int
)
