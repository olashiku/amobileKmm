package com.exquisite.a_mobile_kmm.feature.address.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateAddressRequestDto(
    val address: String,
    val phone: String,
    val customerId: String
)
