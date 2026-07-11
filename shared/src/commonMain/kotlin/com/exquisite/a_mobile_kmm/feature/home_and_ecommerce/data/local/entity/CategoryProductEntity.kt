package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryProduct")
data class CategoryProductEntity(
    @PrimaryKey
    val categoryId: Int,
    val category: String
)
