package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val sku: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val status: Boolean,
    val weight: Double,
    val isEnabled: Boolean,
    val coverImageUrl: String?,
    val categoryId: Int,
    val categoryName: String,
    val categoryIsEnabled: Boolean,
    val createdAt: String,
    val updateAt: String,
    val images: String // JSON string of image list
)
