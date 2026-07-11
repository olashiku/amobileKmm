package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.data_source

import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.ApartmentTypeDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.CleaningTypeDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.NumberOfRoomsDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.RegionDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.ApartmentTypeEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.CleaningTypeEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.NumberOfRoomsEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.RegionEntity
import kotlinx.coroutines.flow.Flow

class CleaningServiceDataSource(
    private val apartmentTypeDao: ApartmentTypeDao,
    private val cleaningTypeDao: CleaningTypeDao,
    private val regionDao: RegionDao,
    private val numberOfRoomsDao: NumberOfRoomsDao
) {

    fun getAllApartmentTypes(): Flow<List<ApartmentTypeEntity>> {
        return apartmentTypeDao.getAllApartmentTypes()
    }

    suspend fun saveApartmentTypes(apartmentTypes: List<ApartmentTypeEntity>) {
        apartmentTypeDao.insertAll(apartmentTypes)
    }

    fun getAllCleaningTypes(): Flow<List<CleaningTypeEntity>> {
        return cleaningTypeDao.getAllCleaningTypes()
    }

    suspend fun saveCleaningTypes(cleaningTypes: List<CleaningTypeEntity>) {
        cleaningTypeDao.insertAll(cleaningTypes)
    }

    fun getAllRegions(): Flow<List<RegionEntity>> {
        return regionDao.getAllRegions()
    }

    suspend fun saveRegions(regions: List<RegionEntity>) {
        regionDao.insertAll(regions)
    }

    fun getAllNumberOfRooms(): Flow<List<NumberOfRoomsEntity>> {
        return numberOfRoomsDao.getAllNumberOfRooms()
    }

    suspend fun saveNumberOfRooms(numberOfRooms: List<NumberOfRoomsEntity>) {
        numberOfRoomsDao.insertAll(numberOfRooms)
    }
}
