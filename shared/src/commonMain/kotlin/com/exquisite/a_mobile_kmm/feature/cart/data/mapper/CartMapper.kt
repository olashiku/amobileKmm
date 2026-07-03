package com.exquisite.a_mobile_kmm.feature.cart.data.mapper

import com.exquisite.a_mobile_kmm.feature.cart.data.local.entity.CartEntity
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel

fun CartEntity.cartEntityToCartModel(): CartModel {
    return CartModel(
        this.productId,
        this.productName,
        this.productImage,
        this.productPrice,
        this.quantity
    )
}

fun CartModel.cartModelToCartEntity(): CartEntity {
    return CartEntity(
        productId =    this.productId,
        productName =  this.productName,
        productImage=  this.productImage,
        productPrice=    this.productPrice,
        quantity=   this.quantity
    )
}