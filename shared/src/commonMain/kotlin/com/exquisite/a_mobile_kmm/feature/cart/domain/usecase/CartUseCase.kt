package com.exquisite.a_mobile_kmm.feature.cart.domain.usecase

import com.exquisite.a_mobile_kmm.feature.cart.data.local.data_source.CartDataSource
import com.exquisite.a_mobile_kmm.feature.cart.data.mapper.cartEntityToCartModel
import com.exquisite.a_mobile_kmm.feature.cart.data.mapper.cartModelToCartEntity
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class CartUseCase(private val cartDataSource: CartDataSource) {

     fun getAllItems(): Flow<List<CartModel>> {
        return cartDataSource.getAllItems().mapLatest { it ->
            it.map { it.cartEntityToCartModel() }
        }
    }

    suspend fun addItem(cartModel: CartModel) {
        if(exists(cartModel.productId)){
            incrementQuantity(cartModel.productId,cartModel.quantity)
        }else {
            val item = cartModel.cartModelToCartEntity()
            cartDataSource.addItem(item)
        }
    }

    suspend fun removeItem(productId: Int, quantity: Int = 1,currentQuantity:Int) {
       if(currentQuantity <=1){
           removeItemById(productId)
       } else {
           decrementQuantity(productId, quantity)
       }
    }

    suspend fun incrementQuantity(productId: Int, quantity: Int = 1) {
        cartDataSource.incrementQuantity(productId, quantity)
    }

    suspend fun decrementQuantity(productId: Int, quantity: Int = 1) {
        cartDataSource.decrementQuantity(productId, quantity)
    }

    suspend fun exists(productId: Int): Boolean {
        return cartDataSource.exists(productId)
    }



    suspend fun removeItemById(id: Int) {
        cartDataSource.removeItem(id)
    }

    suspend fun clearCart() {
        cartDataSource.clearCart()
    }


    fun getTotalQuantity(): Flow<Int> {
        return cartDataSource.getTotalQuantity()
    }
}

