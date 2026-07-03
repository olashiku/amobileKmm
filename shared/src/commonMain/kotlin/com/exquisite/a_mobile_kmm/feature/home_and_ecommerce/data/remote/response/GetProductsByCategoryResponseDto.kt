package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetProductsByCategoryResponseDto(
    @Serializable(with = ProductListSerializer::class)
    val data: List<ProductItemDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object ProductListSerializer :
    EmptyObjectAsNullSerializer<List<ProductItemDto>>(ListSerializer(ProductItemDto.serializer()))
