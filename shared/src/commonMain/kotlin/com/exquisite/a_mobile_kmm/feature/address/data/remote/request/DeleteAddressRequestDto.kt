package com.exquisite.a_mobile_kmm.feature.address.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAddressRequestDto(
    val addressId: Int,
    val customerId: Int
)
