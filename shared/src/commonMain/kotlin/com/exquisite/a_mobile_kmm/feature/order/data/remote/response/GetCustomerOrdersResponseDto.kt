package com.exquisite.a_mobile_kmm.feature.order.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CategoryDto
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetCustomerOrdersResponseDto(
    @Serializable(with = CustomerOrderListSerializer::class)
    val data: List<CustomerOrderDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object CustomerOrderListSerializer :
    EmptyObjectAsNullSerializer<List<CustomerOrderDto>>(ListSerializer(CustomerOrderDto.serializer()))

@Serializable
data class CustomerOrderDto(
    val orders: OrderDto? = null,
    val orderDetails: List<OrderDetailDto>? = null,
    val shipping: ShippingDto? = null
)

@Serializable
data class OrderDto(
    val id: Int? = null,
    val status: String? = null,
    val ref: String? = null,
    val amount: Double? = null,
    val taxAmount: Double? = null,
    val totalAmount: Double? = null,
    val address: AddressDto? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class AddressDto(
    val id: Int? = null,
    val address: String? = null,
    val phone: String? = null,
    val addressCode: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class OrderDetailDto(
    val id: Int? = null,
    val product: OrderProductDto? = null,
    val order: OrderDto? = null,
    val quantity: Int? = null,
    val amount: Double? = null,
    val ref: String? = null
)

@Serializable
data class OrderProductDto(
    val id: Int? = null,
    val sku: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val quantity: Int? = null,
    val status: Boolean? = null,
    val weight: Double? = null,
    val isEnabled: Boolean? = null,
    val coverImageUrl: String? = null,
    val category: CategoryDto? = null,
    val createdAt: String? = null,
    val updateAt: String? = null
)

@Serializable
data class ShippingDto(
    val id: Int? = null,
    val ref: String? = null,
    val request_token: String? = null,
    val courier_id: String? = null,
    val shipping_amount: String? = null,
    val service_code: String? = null,
    val status: Boolean? = null,
    val tracking_url: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)
