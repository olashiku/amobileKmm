package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.ApartmentTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApartmentTypeDao {

    @Query("SELECT * FROM ApartmentType")
    fun getAllApartmentTypes(): Flow<List<ApartmentTypeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apartmentTypes: List<ApartmentTypeEntity>)

    @Query("DELETE FROM ApartmentType")
    suspend fun deleteAll()
}
