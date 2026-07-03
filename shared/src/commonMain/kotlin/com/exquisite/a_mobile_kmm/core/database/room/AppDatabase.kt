package com.exquisite.a_mobile_kmm.core.database.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.exquisite.a_mobile_kmm.feature.cart.data.local.dao.CartDao
import com.exquisite.a_mobile_kmm.feature.cart.data.local.entity.CartEntity

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(entities = [CartEntity::class], version = 2, exportSchema = true)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}


