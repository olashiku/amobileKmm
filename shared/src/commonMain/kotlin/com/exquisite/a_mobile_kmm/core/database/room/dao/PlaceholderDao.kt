package com.exquisite.a_mobile_kmm.core.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.exquisite.a_mobile_kmm.core.database.room.entity.PlaceholderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceholderDao {
    @Query("SELECT * FROM app_metadata WHERE `key` = :key")
    fun getValue(key: String): Flow<PlaceholderEntity>
}