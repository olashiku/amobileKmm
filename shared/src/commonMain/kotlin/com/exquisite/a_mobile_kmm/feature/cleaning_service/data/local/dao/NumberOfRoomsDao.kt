package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.NumberOfRoomsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NumberOfRoomsDao {

    @Query("SELECT * FROM NumberOfRooms")
    fun getAllNumberOfRooms(): Flow<List<NumberOfRoomsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(numberOfRooms: List<NumberOfRoomsEntity>)

    @Query("DELETE FROM NumberOfRooms")
    suspend fun deleteAll()
}
