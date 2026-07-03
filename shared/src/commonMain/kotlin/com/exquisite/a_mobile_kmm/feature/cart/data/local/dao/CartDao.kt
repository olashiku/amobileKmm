package com.exquisite.a_mobile_kmm.feature.cart.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.exquisite.a_mobile_kmm.feature.cart.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao  {

    @Query("SELECT * FROM Cart")
     fun getAllItems(): Flow<List<CartEntity>>

     @Insert
     suspend fun addItem(item: CartEntity)

    @Query("UPDATE Cart SET quantity = quantity + :quantity WHERE productId = :productId")
    suspend fun incrementQuantity(productId: Int, quantity: Int = 1)

    @Query("UPDATE Cart SET quantity = quantity - :quantity WHERE productId = :productId")
    suspend fun decrementQuantity(productId: Int, quantity: Int = 1)

    @Query("SELECT EXISTS(SELECT 1 FROM Cart WHERE productId = :productId)")
    suspend fun exists(productId: Int): Boolean

    @Query("DELETE FROM Cart WHERE productId = :id")
    suspend  fun removeItem(id: Int)

    @Update
    suspend  fun updateItem(item: CartEntity)

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM Cart")
    fun getTotalQuantity(): Flow<Int>
}