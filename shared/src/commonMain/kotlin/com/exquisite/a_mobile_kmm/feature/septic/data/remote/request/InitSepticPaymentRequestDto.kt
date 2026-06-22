package com.exquisite.a_mobile_kmm.feature.septic.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitSepticPaymentRequestDto(
    val customerId: Int,
    val fullName: String,
    val phoneNo: String,
    val email: String,
    val address: String,
    val dateOfExcavation: String,
    val timeOfExcavation: String,
    val specialNote: String,
    val septicSizeId: Int
)
