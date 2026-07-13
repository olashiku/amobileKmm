package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

data class DebitFromWalletBasicCleaningPaymentRequest(
    val reference: String,
    val apartmentTypeId: Int,
    val images: List<String>,
    val regionId: Int,
    val locationId: Int,
    val address: String,
    val customerId: Int
)
