package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.mapper

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CreateOrderRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.OrderProductDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CreateOrderRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.OrderProduct

fun CreateOrderRequest.toDto(): CreateOrderRequestDto {
    return CreateOrderRequestDto(
        customerId = this.customerId,
        addressId = this.addressId,
        products = this.products.map { it.toDto() }
    )
}

fun OrderProduct.toDto(): OrderProductDto {
    return OrderProductDto(
        productId = this.productId.toString(),
        quantity = this.quantity.toString()
    )
}
