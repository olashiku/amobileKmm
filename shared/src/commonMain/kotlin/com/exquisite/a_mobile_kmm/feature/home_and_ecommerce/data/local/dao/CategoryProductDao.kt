package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.CategoryProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryProductDao {

    @Query("SELECT * FROM CategoryProduct")
    fun getAllCategoryProducts(): Flow<List<CategoryProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categoryProducts: List<CategoryProductEntity>)

    @Query("DELETE FROM CategoryProduct")
    suspend fun deleteAll()
}
