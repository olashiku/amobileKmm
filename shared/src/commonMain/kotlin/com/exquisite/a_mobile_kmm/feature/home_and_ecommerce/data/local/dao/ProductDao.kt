package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Product WHERE categoryId = :categoryId")
    fun getProductsByCategory(categoryId: Int): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM Product")
    suspend fun deleteAll()
}
