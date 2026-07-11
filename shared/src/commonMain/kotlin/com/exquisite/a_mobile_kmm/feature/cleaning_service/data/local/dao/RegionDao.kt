package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.RegionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {

    @Query("SELECT * FROM Region")
    fun getAllRegions(): Flow<List<RegionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(regions: List<RegionEntity>)

    @Query("DELETE FROM Region")
    suspend fun deleteAll()
}
