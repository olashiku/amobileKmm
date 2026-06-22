package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderResponseDto(
    @Serializable(with = CreateOrderDataSerializer::class)
    val data: CreateOrderDataDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object CreateOrderDataSerializer :
    EmptyObjectAsNullSerializer<CreateOrderDataDto>(CreateOrderDataDto.serializer())

@Serializable
data class CreateOrderDataDto(
    val order_ref: String? = null,
    val request_token: String? = null,
    val shippingDetails: List<ShippingDetailDto>? = null
)

@Serializable
data class ShippingDetailDto(
    val courier_name: String? = null,
    val courier_id: String? = null,
    val courier_image: String? = null,
    val total: Double? = null,
    val pickup_eta: String? = null,
    val pickup_eta_time: String? = null,
    val delivery_eta: String? = null,
    val delivery_eta_time: String? = null,
    val service_code: String? = null
)
