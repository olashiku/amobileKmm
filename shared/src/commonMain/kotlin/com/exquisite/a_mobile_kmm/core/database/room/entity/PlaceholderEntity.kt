package com.exquisite.a_mobile_kmm.core.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_metadata")
data class PlaceholderEntity (
    @PrimaryKey
    val key: String,
    val value: String
)