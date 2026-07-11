package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Region")
data class RegionEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)
