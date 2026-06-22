package com.exquisite.a_mobile_kmm.feature.order.domain.model

data class CustomerOrdersModel(
    val orders: List<CustomerOrder>
)

data class CustomerOrder(
    val order: Order,
    val orderDetails: List<OrderDetail>,
    val shipping: Shipping?
)

data class Order(
    val id: Int,
    val status: String,
    val ref: String,
    val amount: Double,
    val taxAmount: Double,
    val totalAmount: Double?,
    val address: Address,
    val createdAt: String,
    val updatedAt: String
)

data class Address(
    val id: Int,
    val address: String,
    val phone: String?,
    val addressCode: Int,
    val createdAt: String,
    val updatedAt: String
)

data class OrderDetail(
    val id: Int,
    val product: OrderProduct,
    val quantity: Int,
    val amount: Double,
    val ref: String
)

data class OrderProduct(
    val id: Int,
    val sku: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val status: Boolean,
    val weight: Double,
    val isEnabled: Boolean,
    val coverImageUrl: String,
    val categoryName: String,
    val createdAt: String,
    val updateAt: String
)

data class Shipping(
    val id: Int,
    val ref: String,
    val requestToken: String,
    val courierId: String,
    val shippingAmount: String,
    val serviceCode: String,
    val status: Boolean,
    val trackingUrl: String?,
    val createdAt: String,
    val updatedAt: String
)
