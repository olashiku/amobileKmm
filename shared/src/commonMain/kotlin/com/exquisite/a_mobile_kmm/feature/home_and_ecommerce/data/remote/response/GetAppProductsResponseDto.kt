package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetAppProductsResponseDto(
    @Serializable(with = CategoryProductListSerializer::class)
    val data: List<CategoryProductDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object CategoryProductListSerializer :
    EmptyObjectAsNullSerializer<List<CategoryProductDto>>(ListSerializer(CategoryProductDto.serializer()))

@Serializable
data class CategoryProductDto(
    val categoryId: Int? = null,
    val category: String? = null,
    val product: List<ProductItemDto>? = null
)

@Serializable
data class ProductItemDto(
    val product: ProductDto? = null,
    val image: List<String>? = null
)

@Serializable
data class ProductDto(
    val id: Int? = null,
    val sku: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val quantity: Int? = null,
    val status: Boolean? = null,
    val weight: Double? = null,
    val isEnabled: Boolean? = null,
    val coverImageUrl: String? = null,
    val category: CategoryDto? = null,
    val createdAt: String? = null,
    val updateAt: String? = null
)

@Serializable
data class CategoryDto(
    val id: Int? = null,
    val name: String? = null,
    val isEnabled: Boolean? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
