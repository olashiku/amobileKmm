package com.exquisite.a_mobile_kmm.feature.cart.data.local.data_source

import com.exquisite.a_mobile_kmm.feature.cart.data.local.dao.CartDao
import com.exquisite.a_mobile_kmm.feature.cart.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

class CartDataSource(val cartDao: CartDao) {

      fun getAllItems(): Flow<List<CartEntity>>{
        return cartDao.getAllItems()
    }

    suspend fun addItem(item: CartEntity){
        cartDao.addItem(item)
    }

    suspend fun incrementQuantity(productId: Int, quantity: Int = 1){
        cartDao.incrementQuantity(productId, quantity)
    }

    suspend fun decrementQuantity(productId: Int, quantity: Int = 1){
        cartDao.decrementQuantity(productId, quantity)
    }

    suspend fun exists(productId: Int): Boolean{
        return cartDao.exists(productId)
    }

    suspend  fun removeItem(id: Int){
        cartDao.removeItem(id)
    }

    suspend  fun updateItem(item: CartEntity){
        cartDao.updateItem(item)
    }

    fun getTotalQuantity(): Flow<Int>{
        return cartDao.getTotalQuantity()
    }
}