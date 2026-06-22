package com.exquisite.a_mobile_kmm.feature.order.data.mapper

import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.AddressDto
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.CustomerOrderDto
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.GetCustomerOrdersResponseDto
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.OrderDetailDto
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.OrderDto
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.OrderProductDto
import com.exquisite.a_mobile_kmm.feature.order.data.remote.response.ShippingDto
import com.exquisite.a_mobile_kmm.feature.order.domain.model.Address
import com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrder
import com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrdersModel
import com.exquisite.a_mobile_kmm.feature.order.domain.model.Order
import com.exquisite.a_mobile_kmm.feature.order.domain.model.OrderDetail
import com.exquisite.a_mobile_kmm.feature.order.domain.model.OrderProduct
import com.exquisite.a_mobile_kmm.feature.order.domain.model.Shipping

/**
 * Maps GetCustomerOrdersResponseDto to CustomerOrdersModel domain model
 * Returns null if any required fields are missing
 */
fun GetCustomerOrdersResponseDto.toCustomerOrdersModel(): CustomerOrdersModel? {
    val ordersList = data?.mapNotNull { it.toDomainModel() } ?: return null
    return if (ordersList.isNotEmpty()) {
        CustomerOrdersModel(orders = ordersList)
    } else {
        null
    }
}

/**
 * Maps CustomerOrderDto to CustomerOrder domain model
 * Returns null if any required fields are missing
 */
fun CustomerOrderDto.toDomainModel(): CustomerOrder? {
    val domainOrder = orders?.toDomainModel() ?: return null
    val domainOrderDetails = orderDetails?.mapNotNull { it.toDomainModel() } ?: return null
    val domainShipping = shipping?.toDomainModel()

    return CustomerOrder(
        order = domainOrder,
        orderDetails = domainOrderDetails,
        shipping = domainShipping
    )
}

/**
 * Maps OrderDto to Order domain model
 * Returns null if any required fields are missing
 */
fun OrderDto.toDomainModel(): Order? {
    return if (id != null &&
        status != null &&
        ref != null &&
        amount != null &&
        taxAmount != null &&
        address != null &&
        createdAt != null &&
        updatedAt != null) {
        val domainAddress = address.toDomainModel() ?: return null
        Order(
            id = id,
            status = status,
            ref = ref,
            amount = amount,
            taxAmount = taxAmount,
            totalAmount = totalAmount,
            address = domainAddress,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

/**
 * Maps AddressDto to Address domain model
 * Returns null if any required fields are missing
 */
fun AddressDto.toDomainModel(): Address? {
    return if (id != null &&
        address != null &&
        addressCode != null &&
        createdAt != null &&
        updatedAt != null) {
        Address(
            id = id,
            address = address,
            phone = phone,
            addressCode = addressCode,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

/**
 * Maps OrderDetailDto to OrderDetail domain model
 * Returns null if any required fields are missing
 */
fun OrderDetailDto.toDomainModel(): OrderDetail? {
    return if (id != null &&
        product != null &&
        quantity != null &&
        amount != null &&
        ref != null) {
        val domainProduct = product.toDomainModel() ?: return null
        OrderDetail(
            id = id,
            product = domainProduct,
            quantity = quantity,
            amount = amount,
            ref = ref
        )
    } else {
        null
    }
}

/**
 * Maps OrderProductDto to OrderProduct domain model
 * Returns null if any required fields are missing
 */
fun OrderProductDto.toDomainModel(): OrderProduct? {
    return if (id != null &&
        sku != null &&
        name != null &&
        description != null &&
        price != null &&
        quantity != null &&
        status != null &&
        weight != null &&
        isEnabled != null &&
        coverImageUrl != null &&
        category != null &&
        createdAt != null &&
        updateAt != null) {
        OrderProduct(
            id = id,
            sku = sku,
            name = name,
            description = description,
            price = price,
            quantity = quantity,
            status = status,
            weight = weight,
            isEnabled = isEnabled,
            coverImageUrl = coverImageUrl,
            categoryName = category.name ?: "",
            createdAt = createdAt,
            updateAt = updateAt
        )
    } else {
        null
    }
}

/**
 * Maps ShippingDto to Shipping domain model
 * Returns null if any required fields are missing
 */
fun ShippingDto.toDomainModel(): Shipping? {
    return if (id != null &&
        ref != null &&
        request_token != null &&
        courier_id != null &&
        shipping_amount != null &&
        service_code != null &&
        status != null &&
        created_at != null &&
        updated_at != null) {
        Shipping(
            id = id,
            ref = ref,
            requestToken = request_token,
            courierId = courier_id,
            shippingAmount = shipping_amount,
            serviceCode = service_code,
            status = status,
            trackingUrl = tracking_url,
            createdAt = created_at,
            updatedAt = updated_at
        )
    } else {
        null
    }
}
