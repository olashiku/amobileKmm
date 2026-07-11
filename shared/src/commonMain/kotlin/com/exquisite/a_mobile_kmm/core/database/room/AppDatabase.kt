package com.exquisite.a_mobile_kmm.core.database.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.exquisite.a_mobile_kmm.feature.cart.data.local.dao.CartDao
import com.exquisite.a_mobile_kmm.feature.cart.data.local.entity.CartEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.ApartmentTypeDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.CleaningTypeDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.NumberOfRoomsDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.dao.RegionDao
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.ApartmentTypeEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.CleaningTypeEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.NumberOfRoomsEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.RegionEntity
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.dao.CategoryProductDao
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.dao.ProductDao
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.CategoryProductEntity
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.ProductEntity

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(
    entities = [
        CartEntity::class,
        ApartmentTypeEntity::class,
        CleaningTypeEntity::class,
        RegionEntity::class,
        NumberOfRoomsEntity::class,
        CategoryProductEntity::class,
        ProductEntity::class
    ],
    version = 5,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun apartmentTypeDao(): ApartmentTypeDao
    abstract fun cleaningTypeDao(): CleaningTypeDao
    abstract fun regionDao(): RegionDao
    abstract fun numberOfRoomsDao(): NumberOfRoomsDao
    abstract fun categoryProductDao(): CategoryProductDao
    abstract fun productDao(): ProductDao
}


