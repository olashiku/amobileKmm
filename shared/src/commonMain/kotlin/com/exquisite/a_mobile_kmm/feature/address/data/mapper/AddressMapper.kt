package com.exquisite.a_mobile_kmm.feature.address.data.mapper

import com.exquisite.a_mobile_kmm.feature.address.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.address.domain.model.*

fun GetAddressesResponseDto.toAddressModelList(): List<AddressModel>? {
    return data?.map { it.toAddressModel() }
}

fun AddressDto.toAddressModel(): AddressModel {
    return AddressModel(
        id = id,
        phone = phone,
        address = address,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun UpdateAddressResponseDto.toAddressResponseModel(): AddressResponseModel {
    return AddressResponseModel(message = responseMessage)
}

fun CreateAddressResponseDto.toAddressResponseModel(): AddressResponseModel {
    return AddressResponseModel(message = responseMessage)
}

fun DeleteAddressResponseDto.toAddressResponseModel(): AddressResponseModel {
    return AddressResponseModel(message = responseMessage)
}
