package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.mapper

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.CategoryProductEntity
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.ProductEntity
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun CategoryProductDto.toCategoryEntity(): CategoryProductEntity {
    return CategoryProductEntity(
        categoryId = this.categoryId ?: 0,
        category = this.category ?: ""
    )
}

fun ProductItemDto.toProductEntity(categoryId: Int, categoryName: String, categoryIsEnabled: Boolean): ProductEntity? {
    val prod = this.product ?: return null
    return ProductEntity(
        id = prod.id ?: 0,
        sku = prod.sku ?: "",
        name = prod.name ?: "",
        description = prod.description ?: "",
        price = prod.price ?: 0.0,
        quantity = prod.quantity ?: 0,
        status = prod.status ?: false,
        weight = prod.weight ?: 0.0,
        isEnabled = prod.isEnabled ?: false,
        coverImageUrl = prod.coverImageUrl,
        categoryId = categoryId,
        categoryName = categoryName,
        categoryIsEnabled = categoryIsEnabled,
        createdAt = prod.createdAt ?: "",
        updateAt = prod.updateAt ?: "",
        images = Json.encodeToString(this.image ?: emptyList())
    )
}

fun List<ProductEntity>.toProductItemDtoList(): List<ProductItemDto> {
    return this.map { entity ->
        ProductItemDto(
            product = ProductDto(
                id = entity.id,
                sku = entity.sku,
                name = entity.name,
                description = entity.description,
                price = entity.price,
                quantity = entity.quantity,
                status = entity.status,
                weight = entity.weight,
                isEnabled = entity.isEnabled,
                coverImageUrl = entity.coverImageUrl,
                category = CategoryDto(
                    id = entity.categoryId,
                    name = entity.categoryName,
                    isEnabled = entity.categoryIsEnabled,
                    createdAt = "",
                    updatedAt = ""
                ),
                createdAt = entity.createdAt,
                updateAt = entity.updateAt
            ),
            image = try {
                Json.decodeFromString<List<String>>(entity.images)
            } catch (e: Exception) {
                emptyList()
            }
        )
    }
}
