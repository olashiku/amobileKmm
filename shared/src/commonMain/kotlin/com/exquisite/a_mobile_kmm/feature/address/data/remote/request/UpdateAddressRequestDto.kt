package com.exquisite.a_mobile_kmm.feature.address.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAddressRequestDto(
    val address: String,
    val customerId: Int,
    val addressId: Int
)
