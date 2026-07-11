package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.CleaningTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CleaningTypeDao {

    @Query("SELECT * FROM CleaningType")
    fun getAllCleaningTypes(): Flow<List<CleaningTypeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cleaningTypes: List<CleaningTypeEntity>)

    @Query("DELETE FROM CleaningType")
    suspend fun deleteAll()
}
