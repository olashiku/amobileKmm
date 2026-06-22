package com.exquisite.a_mobile_kmm.feature.address.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class AddressDto(
    val id: Int = 0,
    val phone: String = "",
    val address: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)

@Serializable
data class GetAddressesResponseDto(
    @Serializable(with = AddressListSerializer::class)
    val data: List<AddressDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object AddressListSerializer :
    EmptyObjectAsNullSerializer<List<AddressDto>>(ListSerializer(AddressDto.serializer()))
