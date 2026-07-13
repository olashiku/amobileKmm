package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

data class InitDeepCleaningPaymentRequest(
    val customerId: Int,
    val regionId: Int,
    val locationId: Int,
    val apartmentTypeId: Int,
    val cleaningTypeId: Int,
    val numberOfRoomsId: Int,
    val isPostConstruction: Boolean,
    val cleaningDate: String,
    val cleaningTime: String,
    val address: String,
    val images: List<String>
)
